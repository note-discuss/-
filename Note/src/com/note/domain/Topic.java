package com.note.domain;

public class Topic {
    private String userid;
    private String Title;
    private String question;
    
    public String getUserId(){
    	return this.userid;
    }
    
    public String getTitle(){
    	return this.Title;
    }
    
    public String getQuestion(){
    	return this.question;
    }
    
    public void setId(String userid){
    	this.userid=userid;
    }
    
    public void setTtile(String Title){
    	this.Title=Title;
    }
    
    public void setQuestion(String Question){
    	this.question=Question;
    }
}
