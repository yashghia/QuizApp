/*Homework 4
StatsActivity
Yash Ghia & Prabhakar teja Seeda*/

package com.example.teja.homework4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {
    public ArrayList<Questions> passBack = new ArrayList<Questions>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        passBack = (ArrayList<Questions>) getIntent().getExtras().getSerializable(MainActivity.TRIVIA_DETAILS);
        setTitle("Trivia Stats");
        ProgressBar pb = (ProgressBar) findViewById(R.id.finalprogressBar);
        Double newString= getIntent().getExtras().getDouble("percentage");
        if (getIntent().getExtras()!=null){
            double grade = (double) getIntent().getExtras().getInt(TriviaActivity.PERCENT_DETAILS);
            int i = (int) grade;
            pb.setProgress(getIntent().getFlags());
            TextView textView = (TextView) findViewById(R.id.textView4);
            textView.setText(String.valueOf(grade));
        }
    }
    public void tryAgain(View view){
        Intent i = new Intent(StatsActivity.this,TriviaActivity.class);
        i.putExtra(MainActivity.TRIVIA_DETAILS,(Serializable)passBack);
        startActivity(i);
    }
    public void quitAct(View view){
        Intent i = new Intent(StatsActivity.this,MainActivity.class);
        startActivity(i);
    }
}
