/*Homework 4
MainActivity
Yash Ghia & Prabhakar teja Seeda*/

package com.example.teja.homework4;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static Bitmap getImage = null;
    public final static String TRIVIA_DETAILS = "TRIVIA";
    public static ArrayList<Questions> QuestionsArray = new ArrayList<>();
    int resultCounter,totalRecipeCount = 0;
    String baseURL = "http://dev.theappsdr.com/apis/trivia_json/index.php";
    Button button;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Trivia");
        button = (Button)findViewById(R.id.triviaButton);
        button.setEnabled(false);
        new GetQuestionsAsyncTask(this).execute(baseURL);
    }
    /*public void startTriviaAction(View view){
        new GetQuestionsAsyncTask(this).execute(baseURL);
    }

    public void moveInterfaces(ArrayList<Questions> questionArray) {
        Intent i = new Intent(MainActivity.this,TriviaActivity.class);
        i.putExtra(TRIVIA_DETAILS, (Serializable) questionArray);
        startActivity(i);
    }*/

    public void startTriviaAction(View view){
        Intent i = new Intent(MainActivity.this,TriviaActivity.class);
        i.putExtra(TRIVIA_DETAILS, (Serializable) QuestionsArray);
        startActivity(i);
    }

    public void moveInterfaces(ArrayList<Questions> questionArray) {
        QuestionsArray = questionArray;
        Log.d("array",""+QuestionsArray.get(9));
        textView = (TextView)findViewById(R.id.readyText);
        textView.setText("Trivia Ready");
        button.setEnabled(true);
    }

    public void exit(View view)
    {
        finish();
        moveTaskToBack(true);
    }

//    @Override
//    public Context getContext(){
//        return this;
//    }
}
