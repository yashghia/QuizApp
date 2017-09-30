/*Homework 4
Questions
Yash Ghia & Prabhakar teja Seeda*/

package com.example.teja.homework4;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by teja on 9/27/17.
 */

public class Questions implements Serializable {
    String id,questionText,imageURL,answer;
    ArrayList<String> optionsArray = new ArrayList<String>();
    public String getQuestionText() {
        return questionText;
    }

    public String getAnswer() {
        return answer;
    }

    public void setOptionsArray(ArrayList<String> optionsArray) {
        this.optionsArray = optionsArray;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public ArrayList<String> getOptionsArray() {
        return optionsArray;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getId() {

        return id;
    }
    static public Questions createResults(JSONObject jsonObject) throws JSONException{
        Questions questions = new Questions();
        questions.setId(jsonObject.getString("id"));
        questions.setQuestionText(jsonObject.getString("text"));
        if (jsonObject.has("image")) {
            questions.setImageURL(jsonObject.getString("image"));
        }else {
            questions.setImageURL("");
        }
        JSONObject choicesJObject = jsonObject.getJSONObject("choices");
        JSONArray choiceArray = choicesJObject.getJSONArray("choice");
        //String choiceType = choicesJObject.getString("answer");
        questions.setAnswer(choicesJObject.getString("answer"));
        int len = choiceArray.length();
        ArrayList<String> choices = new ArrayList<>();
        for(int j=0; j<len; j++) {
            String json = choiceArray.getString(j);
            choices.add(json);
        }
        questions.setOptionsArray(choices);
        Log.d("The choice array","is"+questions.optionsArray);
        Log.d("image",questions.imageURL);
        return questions;
    }

}
