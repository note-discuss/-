package com.example.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note.R;
import com.note.service.UserService;
import com.example.note.R;
import com.example.note.AddTopicActivity;
import com.example.note.UserInfoActivity;
public class ClassNoteActivity extends Activity{
	  EditText note;
	  Button   addtopic;
	  Button   userinfo;
      public void onCreate(Bundle savedInstanceState){
    	  super.onCreate(savedInstanceState);
  		  setContentView(R.layout.note);
  		  findViews();  
      }
      private void findViews(){
    	  addtopic = (Button) findViewById(R.id.addtopic);
    	  userinfo = (Button) findViewById(R.id.userinfo);
  		  addtopic.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent(ClassNoteActivity.this,AddTopicActivity.class);//Æô¶¯register»î¶¯
				startActivity(intent);
			}
		});
  		  userinfo.setOnClickListener(new OnClickListener(){
  			  public void onClick(View v){
  				  Intent intent1=new Intent(ClassNoteActivity.this,UserInfoActivity.class);
  				  startActivity(intent1);
  			  }
  		  });
      }
}
