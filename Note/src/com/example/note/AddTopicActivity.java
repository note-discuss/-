package com.example.note;

import com.note.domain.Topic;
import com.note.service.TopicService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class AddTopicActivity extends Activity{
	Button button;
	EditText title;
	EditText question;
    Intent intent = this.getIntent();
    Bundle bundle = intent.getExtras();
    String userid = bundle.getString("id");
    public void onCreate(Bundle savedInstanceState){
  	  super.onCreate(savedInstanceState);
		  setContentView(R.layout.addtopic);
		  findViews();  
    }
    private void findViews(){
       button = (Button)findViewById(R.id.publish);
  	   title  = (EditText)findViewById(R.id.title);
  	   question = (EditText)findViewById(R.id.question);
  	   button.setOnClickListener(new OnClickListener(){
  		   public void onClick(View v){
  			   String titlestr=button.getText().toString();
  			   String questionstr=question.getText().toString();
               TopicService topicservice = new TopicService(AddTopicActivity.this);
               Topic topic = new Topic(userid,titlestr,questionstr);
               boolean f = topicservice.insert(topic);
               if(f){
            	   Log.i("TAG","发布成功!");  
            	   Toast.makeText(AddTopicActivity.this, "发布成功！", Toast.LENGTH_LONG).show();
               }
  		   }
  	   });
    }
}
