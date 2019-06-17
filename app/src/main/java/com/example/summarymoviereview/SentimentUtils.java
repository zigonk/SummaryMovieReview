package com.example.summarymoviereview;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.services.language.v1beta2.CloudNaturalLanguage;
import com.google.api.services.language.v1beta2.CloudNaturalLanguageRequestInitializer;
import com.google.api.services.language.v1beta2.model.AnnotateTextRequest;
import com.google.api.services.language.v1beta2.model.AnnotateTextResponse;
import com.google.api.services.language.v1beta2.model.Document;
import com.google.api.services.language.v1beta2.model.Entity;
import com.google.api.services.language.v1beta2.model.Features;
import com.google.api.services.language.v1beta2.model.Sentiment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SentimentUtils {

    private static final String CLOUD_API_KEY = "AIzaSyAD4B8m4rOm0fGAP91leIgPaWFucXzOiVI";

    static final CloudNaturalLanguage naturalLanguageService =
            new CloudNaturalLanguage.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(),
                    null
            ).setCloudNaturalLanguageRequestInitializer(
                    new CloudNaturalLanguageRequestInitializer(CLOUD_API_KEY)
            ).build();

    public static class SentimentReview extends AsyncTask<ReviewObject, Void, ReviewObject> {

        private UpdateSentiment mUpdateSentiment;
        private int mPos;

        public SentimentReview(UpdateSentiment updateSentiment, int pos) {
            mUpdateSentiment = updateSentiment;
            mPos = pos;
        }

        @Override
        protected ReviewObject doInBackground(ReviewObject... reviewObjects) {
            if (reviewObjects.length == 0) return null;
            ReviewObject review = reviewObjects[0];
            AnnotateTextRequest request = createRequest(review.content);

            AnnotateTextResponse response = null;
            try {
                response = naturalLanguageService.documents()
                        .annotateText(request).execute();
                List<Entity> entityList = response.getEntities();
                final float sentiment = response.getDocumentSentiment().getScore();

                // set entity and sentiment for review

                review.entities = combineEntities(entityList);
                review.sentiment = sentiment;

                return review;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ReviewObject reviewObject) {
            super.onPostExecute(reviewObject);
            Log.d("Sentiment", String.valueOf(reviewObject.sentiment));
            mUpdateSentiment.updateSentiment(reviewObject, mPos);
        }
    }

    // Combine two entity has the same name
    // New magnitude equal to sum of all old magnitude
    // New score equal to sum of all (old magnitude * old score)
    // Note: If want to combine two entity lists then you have to create a list has elements of them.

    public static ArrayList<Entity> combineEntities(List<Entity> entityList) {
        ArrayList<Entity> newEntityList = new ArrayList<>();
        for (Entity entity:entityList) {
            boolean check = false;
            for (Entity existEntity:newEntityList) {
                if (entity.getName() == existEntity.getName()) {
                    check = true;
                    float magnitude = entity.getSentiment().getMagnitude();
                    float eMagnitude = existEntity.getSentiment().getMagnitude();
                    float score = entity.getSentiment().getScore();
                    float eScore = existEntity.getSentiment().getScore();
                    eMagnitude += magnitude;
                    eScore += score*magnitude;

                    Sentiment sentiment = new Sentiment();
                    sentiment.setMagnitude(eMagnitude);
                    sentiment.setScore(eScore);

                    existEntity.setSentiment(sentiment);
                }
            }
            if (!check) {
                newEntityList.add(entity);
            }
        }
        return newEntityList;
    }


    private static AnnotateTextRequest createRequest(String transcript) {
        Document document = new Document();
        document.setType("PLAIN_TEXT");
        document.setLanguage("en-US");
        document.setContent(transcript);

        Features features = new Features();
        features.setExtractDocumentSentiment(true);
        features.setExtractEntitySentiment(true);

        final AnnotateTextRequest request = new AnnotateTextRequest();
        request.setDocument(document);
        request.setFeatures(features);
        return request;
    }
}
