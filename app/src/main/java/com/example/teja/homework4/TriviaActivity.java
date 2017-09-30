/*Homework 4
TriviaActivity
Yash Ghia & Prabhakar teja Seeda*/

package com.example.teja.homework4;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.io.Serializable;
import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity {

    public ArrayList<LinearLayout> getText = new ArrayList<LinearLayout>();
    public ArrayList<String> choiceArray = new ArrayList<String>();
    public ArrayList<Questions> answers = new ArrayList<Questions>();
    public final static String PERCENT_DETAILS = "percentage";
    public int radioButtonId=0,nextMove=0,calculateScore=0,setCounter=0,questionNumber=2;
    public RadioGroup radioGroup;
    TextView setQuestionText;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        //ScrollView sv_main = (ScrollView) findViewById(R.id.scrollView);
        final Button timer = (Button) findViewById(R.id.timer);
        setTitle("Questions");

        radioGroup = (RadioGroup) findViewById(R.id.answerGroups);
        answers = (ArrayList<Questions>) getIntent().getExtras().getSerializable(MainActivity.TRIVIA_DETAILS);
        createRadioButtons(answers.get(0));

        countDownTimer = new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText("Time Left: " + millisUntilFinished / 1000 + " seconds");
            }

            public void onFinish() {
                Toast.makeText(TriviaActivity.this, "Time Over", Toast.LENGTH_LONG).show();
                Intent i = new Intent(TriviaActivity.this, StatsActivity.class);
                int finalSize = answers.size();
                float progress1 = (float) calculateScore / (float) finalSize;
                double progress = progress1 * 100;
                i.putExtra(PERCENT_DETAILS, (int) progress);
                i.putExtra(MainActivity.TRIVIA_DETAILS, (Serializable) answers);
                i.setFlags((int) progress);
                startActivity(i);
            }
        };
        countDownTimer.start();
    }

    public void createRadioButtons(Questions question){
        setQuestionText = (TextView) findViewById(R.id.question);
        setQuestionText.setText(question.getQuestionText());
        choiceArray = question.getOptionsArray();
        if (question.getImageURL()!=""){
            new GetImageTask(this).execute(question.getImageURL());
        }
        radioButtonId = 1;
        radioGroup = (RadioGroup) findViewById(R.id.answerGroups);
        radioGroup.removeAllViews();
        for (int i=0; i<choiceArray.size();i++)
        {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(radioButtonId);
            radioButton.setText(choiceArray.get(i).toString());
            radioGroup.addView(radioButton);
            radioButtonId++;
        }
        setCounter++;
    }

    public void nextQuestion(View view) {
        if (setCounter < answers.size()) {
            int answerButton = radioGroup.getCheckedRadioButtonId();
            if (answerButton >= 0) {
                String answer = Integer.toString(answerButton);
                Questions getData = answers.get(nextMove);
                String finalAnswer = getData.getAnswer();
                if (finalAnswer.equals(answer)) {
                    calculateScore++;
                }
                nextMove++;
                TextView txt = (TextView) findViewById(R.id.questionNumber);
                String str = String.valueOf(questionNumber++);
                txt.setText("Q"+str);
                //txt.setText("");
                createRadioButtons(answers.get(nextMove));
            } else {
                Toast.makeText(TriviaActivity.this, "You have not selected any answer", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(TriviaActivity.this, "End of the quiz", Toast.LENGTH_LONG).show();
            countDownTimer.cancel();
            Intent i = new Intent(TriviaActivity.this, StatsActivity.class);
            int finalSize = answers.size();
            float progress1 = (float)calculateScore/(float)finalSize;
            double progress = progress1*100;
            i.putExtra(PERCENT_DETAILS,(int)progress);
            i.putExtra(MainActivity.TRIVIA_DETAILS, (Serializable) answers);
            i.setFlags((int)progress);
            startActivity(i);
        }
    }
    public void quitAction(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you really want to quit the quiz?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        countDownTimer.cancel();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("delete","delete no");
                //alertDialog.dismiss();
            }
        }).setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
