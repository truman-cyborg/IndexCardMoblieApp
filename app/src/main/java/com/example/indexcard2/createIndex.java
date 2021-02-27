package com.example.indexcard2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class createIndex extends AppCompatActivity  {

    index index;
    TextView listOfIndex;
    boolean aNewIndex;
    int position;
    boolean switcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_index);
        switcher = true;
        index = (index)getIntent().getSerializableExtra("index");
        aNewIndex = getIntent().getBooleanExtra("isNew?", false);

        //setting the name on the activity from coming from QandA and MainActivity activties
        TextView name = findViewById(R.id.name);
        name.setText(index.getName());


        //bringing the arraylist "question" into the listview
        position = 0 ;

        listOfIndex = findViewById(R.id.indexList);
        if(index.getQuestion().isEmpty() != true){
            listOfIndex.setText(index.getQuestion().get(0));
            listOfIndex.setOnTouchListener(new OnSwipeTouchListener(createIndex.this) {
                public void onSwipeRight() {
                    //show the pervious index
                    if(position != 0){
                        position--;
                        listOfIndex.setText(index.getQuestion().get(position));
                    }
                    Toast.makeText(createIndex.this, "right" + position, Toast.LENGTH_SHORT).show();

                }
                public void onSwipeLeft() {
                    //show the next index
                    if(position != index.getQuestion().size() -1 ){
                        position++;
                        listOfIndex.setText(index.getQuestion().get(position));
                        Toast.makeText(createIndex.this, "left"  + position, Toast.LENGTH_SHORT).show();
                    }

                }

                public void onClick(){
                    Toast.makeText(createIndex.this,String.valueOf(index.getIdNum()),Toast.LENGTH_LONG).show();
                    if(switcher == true){
                        listOfIndex.setText(index.getAnswer().get(position));
                        switcher = false;
                    }else{
                        listOfIndex.setText(index.getQuestion().get(position));
                        switcher = true;
                    }
                }




            });
        }

        /*ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, index.getQuestion());
        listOfIndex.setAdapter(adapter);*/


    }

    public void summitName(View v){

        if(findViewById(R.id.name) != null)
        {
            Intent intent = new Intent(this,MainActivity.class);
            TextView indexName = findViewById(R.id.name);
            index.setName(indexName.getText().toString());
            intent.putExtra("isNew?",  aNewIndex);
            //bundle in order to sent object with serializable
            Bundle bundle= new Bundle();
            bundle.putSerializable("index",index);
            intent.putExtras(bundle);
            startActivity(intent);
        }


    }
//
//    //is a button that creates question and answer
    public void newIndex(View v){

        if(findViewById(R.id.name) != null)
        {
            Intent intent = new Intent(this,QandA.class);
            TextView theFact = findViewById(R.id.name);
            index.setName(theFact.getText().toString());
            intent.putExtra("isNew?",  aNewIndex);
            //bundle in order to sent object with serializable
            Bundle bundle= new Bundle();
            bundle.putSerializable("index",index);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }



    //allow us to switch between answers and questions
    public void switcher(View v){
//        listOfIndex = findViewById(R.id.indexList);
//        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, index.getAnswer());
//        listOfIndex.setAdapter(adapter);
    }


    public void deleteIndex(View v){
    //will delete the current index
    }


}