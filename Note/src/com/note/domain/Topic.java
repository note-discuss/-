package com.note.domain;

public class Topic {
    private String userid;
    private String title;
    private String note;
    private String conclusion;
    
    public Topic(){
    	super();
    }
    
    public Topic(String userid,String title,String note,String conclusion){
    	super();
    	this.userid=userid;
    	this.title = title;
    	this.note = note;
    	this.conclusion=conclusion;
    }
    public String getUserId(){
    	return this.userid;
    }
    
    public String getTitle(){
    	return this.title;
    }
    
    public String getNote(){
    	return this.note;
    }
    
    public String getConclusion(){
    	return this.conclusion;
    }
    
    public void setId(String userid){
    	this.userid=userid;
    }
    
    public void setTitle(String title){
    	this.title=title;
    }
    
    public void setNote(String note){
    	this.note=note;
    }
    
    public void setConclusion(String Conclusion){
    	this.conclusion=conclusion;
    }
}
