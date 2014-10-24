package com.note.domain;

public class Topic {
    private String id;
    private String Title;
    private String question;
    
    public String getId(){
    	return this.id;
    }
    
    public String getTitle(){
    	return this.Title;
    }
    
    public String getQuestion(){
    	return this.question;
    }
    
    public void setId(String Id){
    	this.id=Id;
    }
    
    public void setTtile(String Title){
    	this.Title=Title;
    }
    
    public void setQuestion(String Question){
    	this.question=Question;
    }
}
