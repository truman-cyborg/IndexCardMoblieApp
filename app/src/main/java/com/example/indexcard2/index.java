package com.example.indexcard2;


import java.io.Serializable;
import java.util.ArrayList;

public class index implements Serializable {


    int idNum;
    ArrayList<String> question;
    ArrayList<String> answer;
    String name;

    //constructor
    public index(){
        this.question = new ArrayList<>();
        this.answer = new ArrayList<>();
        this.name = "Insert Name";
    }
    //getters
    public ArrayList<String> getQuestion(){
        return this.question;
    }
    public ArrayList<String> getAnswer(){
        return this.answer;
    }
    public String getName(){
        return  this.name;
    }
    public int getIdNum(){
        return this.idNum;
    }

    //setters
    public void setIdNum(int idNum){
        this.idNum = idNum;
    }
    public void setQuestion(ArrayList<String> question){
        this.question = question;
    }
    public void setAnswer(ArrayList<String> answer){
        this.answer  = answer;
    }
    public void setName(String name){
        this.name = name;
    }

    //methods
    public void addQuestion(String question){
       this.question.add(question);
    }
    public void addAnswer(String answer){
        this.answer.add(answer);
    }

}
