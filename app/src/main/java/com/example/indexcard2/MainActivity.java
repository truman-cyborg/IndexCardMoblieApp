package com.example.indexcard2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    //goal of the app is to make a dynamic index card with a unlimited amount of cards

    //idea
    //main class is where you pick your topic/create new topic/ delete topic
    // each topic will have a id number

    //gotta make a way for when you open, the code keep the pervious index cards already made

    ArrayList<String> wordList;
    LinkedList<index> deleteList;
    LinkedList<index> indexList;
    int amountOfIndex;
    boolean deleteMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deleteMode = false;

        //loading the data from our past save
        loadData();

        //if a NEW index object from createIndex is created and submitted, we add it to the indexList
        //else if the index object isn't new then we update the index using the id assign to it
        if(getIntent().getBooleanExtra("isNew?",false) == true)
        {
            if(getIntent().getSerializableExtra("index") != null)
            {

                indexList.add((index)getIntent().getSerializableExtra("index"));
                wordList.add(indexList.get(indexList.size()-1).getName());
                amountOfIndex++; //when a index is finally added to the indexlist we increment the size
            }
        }
        else if(getIntent().getBooleanExtra("isNew?",false) == false){
            if(getIntent().getSerializableExtra("index") != null)
            {
                index ok = (index)getIntent().getSerializableExtra("index");
                //Toast.makeText(MainActivity.this, String.valueOf(ok.getIdNum()), Toast.LENGTH_SHORT).show();
                indexList.set(ok.getIdNum(), ok);
            }
        }

        //bringing the wordList into the listview
        ListView list = findViewById(R.id.list);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, wordList);
        list.setAdapter(adapter);

        //when clicking on the item in the list that has been created go to
        // the createIndex activity with the index object bases on position in the listview
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),createIndex.class);
                intent.putExtra("index",  indexList.get(position));
                intent.putExtra("isNew?",  false);
                startActivity(intent);
            }
        });

        //save the data after loading the wordList,indexList and amountOfIndex then updating it
         saveData();
    }


   //create a index object with the the idNum set and bring the object to the createIndex activity
    //in order to add more information
    public void createNewIndex(View v){
        index NewIndex = new index();
        NewIndex.setIdNum(amountOfIndex);
        Intent intent = new Intent(this, createIndex.class);
        Bundle bundle= new Bundle();
        bundle.putSerializable("index",NewIndex);
        intent.putExtras(bundle);
        intent.putExtra("isNew?", true);
        startActivity(intent);
    }


    //to save the data for the wordList,indexList and amountOfIndex
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson(); //create a Gson object

        //store all of the arrays
        String jsonWL = gson.toJson(wordList);
        String jsonN = gson.toJson(indexList);


        //putting in the variable into editor
        editor.putInt("amountOfIndex", amountOfIndex);
        editor.putString("wordlist", jsonWL);
        editor.putString("tester", jsonN);
        editor.apply(); //to apply the save
    }

    //to load the data to the wordList,indexList and amountOfIndex
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonWL = sharedPreferences.getString("wordlist", null);
        String jsonV = sharedPreferences.getString("tester", null);

        //creating the types
        Type WL = new TypeToken<ArrayList<String>>() {}.getType();
        Type VV = new TypeToken<LinkedList<index>>(){}.getType();

        //loading the variables

        wordList = gson.fromJson(jsonWL, WL);
        indexList = gson.fromJson(jsonV, VV);
        amountOfIndex = sharedPreferences.getInt("amountOfIndex", 0);


        //if the variable are nulls
        if (wordList == null) {
            wordList = new ArrayList<>();
        }
        if (indexList == null) {
            //indexList = new ArrayList<>();
            indexList = new LinkedList<>();
        }

    }

    // delete  index objects
    public void delete(View v){
       //when click it will check the deletemode value (default false)

        // if false, the listview onclick will change to
        // check the color background of the position click and if it's not red, change it red and add the position to an delete array
        // if the color is red return it back to it's original color and delete it from the deletearray

        //if true, delete all of the index that was in the deletearray from indexList

        Button delete = findViewById(R.id.button);

        if(deleteMode == false)
        {
            //0xffff0000 = red
            //0xFFFFFFFF = white

            delete.setBackgroundColor(0xffff0000);
            ListView list = findViewById(R.id.list);
            deleteList = new LinkedList<>();
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int color = 0;

                    //get the background color and put it in color variable
                    Drawable background = view.getBackground();
                    if (background instanceof ColorDrawable) {
                        color = ((ColorDrawable) background).getColor();

                    }

                    if(color != 0xffff0000){
                        parent.getChildAt(position).setBackgroundColor(0xffff0000);
                        deleteList.add(indexList.get(position));


                   }else{
                       parent.getChildAt(position).setBackgroundColor(0x66f9b0 );
                        deleteList.remove(indexList.get(position));
                   }
                }
            });
            deleteMode = true;
        }else
            {
                delete.setBackgroundResource(R.drawable.custom_button);
                //could be better way to deal with the removal of the index (note for later updates)
                if(deleteList.size() > 0){

                    wordList = new ArrayList<>();
                    //delete the indexList with the deleteList

                    for(int i =0; i < deleteList.size();i++)
                    {
                        indexList.remove(deleteList.get(i));
                        amountOfIndex--;

                    }
                    //then set the idNum again
                    for(int n = 0; n < indexList.size(); n++){
                        indexList.get(n).setIdNum(n);
                    }

                    //set the wordlist again
                    for(int j = 0; j < indexList.size();j++){
                        wordList.add(indexList.get(j).getName());
                    }


                }


                //setting the listview back to it's original state
                ListView list = findViewById(R.id.list);
                ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, wordList);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(),createIndex.class);
                        intent.putExtra("index",  indexList.get(position));
                        intent.putExtra("isNew?",  false);
                        startActivity(intent);
                    }
                });
                saveData();
                deleteMode = false;
            }

    }


}