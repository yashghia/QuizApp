/*Homework 4
GetQuestionsAsyncTask
Yash Ghia & Prabhakar teja Seeda*/

package com.example.teja.homework4;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by teja on 9/27/17.
 */

public class GetQuestionsAsyncTask extends AsyncTask<String, Integer ,ArrayList<Questions>> {
    MainActivity activity;
    Context newContext;
    ProgressBar pb;
    ImageView img;
    public static ArrayList<Questions> Results= null;
    int resultCounter,totalRecipeCount = 0;
    public GetQuestionsAsyncTask(MainActivity activity){
        this.activity = activity;
        pb = (ProgressBar) activity.findViewById(R.id.circular_progress_bar);
        img = (ImageView) activity.findViewById(R.id.imageViewQuestion);
        img.setVisibility(View.INVISIBLE);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//      newContext = activity.getContext();
        pb.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    protected void onPostExecute(ArrayList<Questions> QuestionResults) {
        Results = QuestionResults;
        Log.d("The array list", "is" + Results);

        //totalRecipeCount = recipeResults.size();
        super.onPostExecute(QuestionResults);
       // MainActivity.QuestionsArray = QuestionResults;
        activity.moveInterfaces(Results);
        img.setVisibility(View.VISIBLE);
        pb.setVisibility(ProgressBar.INVISIBLE);
        //pb.setProgress(0);
        Log.d("The array from","is"+Results);

       // passData.moveInterfaces(Results);
    }

    @Override
    protected ArrayList<Questions> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int statusCode = connection.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = reader.readLine();
                while(line!=null){
                    stringBuilder.append(line);
                    line = reader.readLine();
                }
                return parseQuestionsResult(stringBuilder.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Questions> parseQuestionsResult(String in) throws JSONException {
        ArrayList<Questions> questionsArrayList = new ArrayList<>();
        JSONObject rootObject = new JSONObject(in);
        JSONArray questionJSONArray = rootObject.getJSONArray("questions");
        for(int i=0;i<questionJSONArray.length();i++){
            JSONObject questionJSONObject = questionJSONArray.getJSONObject(i);
            questionsArrayList.add(Questions.createResults(questionJSONObject));
            publishProgress(i*100/questionJSONArray.length());
        }
        return questionsArrayList;
    }

}
