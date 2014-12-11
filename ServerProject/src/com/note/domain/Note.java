package com.note.domain;

public class Note {
	private String userid;
	private String title;
	private String note;
	private String conclusion;
	private String date;
	private String site;
	private String member;
	private String id;
	private String topicid;

	public Note() {
		super();
	}

	public Note(String userid, String title, String note, String conclusion,
			String date, String site, String member,String id,String topicid) {
		super();
		this.userid = userid;
		this.title = title;
		this.note = note;
		this.conclusion = conclusion;
		this.date = date;
		this.site = site;
		this.member = member;
		System.out.println("In Note id="+id);
		this.id=id;
		this.topicid=topicid;
	}
	public Note(String userid, String title, String note, String conclusion,
			String date, String site, String member,String topicid) {
		super();
		this.userid = userid;
		this.title = title;
		this.note = note;
		this.conclusion = conclusion;
		this.date = date;
		this.site = site;
		this.member = member;
		this.topicid=topicid;
	}

	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id=id;
	}
	
	public void setTopicid(String topicid){
		this.topicid=topicid;
	}
	
	public String getUserid() {
		return this.userid;
	}

	public String getSite() {
		return this.site;
	}

	public String getMember() {
		return this.member;
	}

	public String getTitle() {
		return this.title;
	}

	public String getNote() {
		return this.note;
	}

	public String getTopicid(){
		return this.topicid;
	}
	
	public String getConclusion() {
		return this.conclusion;
	}

	public String getDate() {
		return this.date;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public void setConclusion(String Conclusion) {
		this.conclusion = conclusion;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
