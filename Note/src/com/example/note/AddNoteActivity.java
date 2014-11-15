package com.example.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.note.service.remoteURL;

public class AddNoteActivity extends Activity {
	static remoteURL remote = new remoteURL();
    private static  String processURL=remote.remoteURL+"addNote.action?";
	private final String processURL_constant  = remote.remoteURL+"addNote.action?";
	private final String processURL_findstring =remote.remoteURL+"jfindString.action?";
	String result=null;
	String myid;
	String topicid;
	Button button;
	EditText title;
	EditText note;
	EditText conclusion;
	EditText site;
	EditText member;
    public void onCreate(Bundle savedInstanceState){
    	  super.onCreate(savedInstanceState);
  		  setContentView(R.layout.addnote);
  		  Intent intent = this.getIntent();
  		  Bundle bundle = intent.getExtras();
  		  myid=bundle.getString("publisher");
  		  topicid=bundle.getString("topicid");
  		  
    }
}
