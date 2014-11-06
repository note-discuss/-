package com.note.domain;

public class Topic {
    private String userid;
    private String title;
    private String note;
    private String conclusion;
    private String date;
    public Topic(){
    	super();
    }
    
    public Topic(String userid,String title,String note,String conclusion,String date){
    	super();
    	this.userid=userid;
    	this.title = title;
    	this.note = note;
    	this.conclusion=conclusion;
    	this.date=date;
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
    
    public String getDate(){
    	return this.date;
    }
    
    public void setUserId(String userid){
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
    
    public void setDate(String date){
    	this.date=date;
    }
}
