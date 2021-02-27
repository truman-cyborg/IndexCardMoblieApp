package com.example.indexcard2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class QandA extends AppCompatActivity {

    index index;
    boolean aNewIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qand);
        aNewIndex = getIntent().getBooleanExtra("isNew?", false);
        //in order to get to the QandA activity, we need to pass a index object to no need for if statement
        index = (index)getIntent().getSerializableExtra("index");

    }

    public void summit(View v){

        TextView answerBox =   findViewById(R.id.AnswerBox);
        String Nanswer  = answerBox.getText().toString();
        TextView questionBox =   findViewById(R.id.QuestionBox);
        String Nquestion  = questionBox.getText().toString();
        if(Nanswer == null || Nquestion == null){
            Toast toast = Toast.makeText(getApplicationContext(), "Both question and answer must be fill out", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        index.addAnswer(Nanswer);
        index.addQuestion(Nquestion);

        Intent intent = new Intent(this,createIndex.class);
        intent.putExtra("isNew?",  aNewIndex);

        //bundle in order to sent object with serializable
        Bundle bundle= new Bundle();
        bundle.putSerializable("index",index);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}