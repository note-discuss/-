package com.note.domain;

public class Topic {
    private String userid;
    private String title;
    private String question;
    
    public Topic(){
    	super();
    }
    
    public Topic(String userid,String title,String question){
    	super();
    	this.userid=userid;
    	this.title = title;
    	this.question = question;
    }
    public String getUserId(){
    	return this.userid;
    }
    
    public String getTitle(){
    	return this.title;
    }
    
    public String getQuestion(){
    	return this.question;
    }
    
    public void setId(String userid){
    	this.userid=userid;
    }
    
    public void setTtile(String title){
    	this.title=title;
    }
    
    public void setQuestion(String Question){
    	this.question=Question;
    }
}
