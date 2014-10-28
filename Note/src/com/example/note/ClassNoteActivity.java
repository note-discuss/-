package com.example.note;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.note.R;
import com.note.service.TopicDatabaseHelper;
import com.note.service.UserService;
import com.example.note.R;
import com.example.note.AddTopicActivity;
import com.example.note.UserInfoActivity;
public class ClassNoteActivity extends ListActivity{
	  EditText note;
	  Button   addtopic;
	  Button   userinfo;
      public void onCreate(Bundle savedInstanceState){
    	  super.onCreate(savedInstanceState);
    	  this.setTitle("课堂讨论笔记");
  		  setContentView(R.layout.note);
  		  showlist();
  		  findViews();  
      }
      private void findViews(){
    	  addtopic = (Button) findViewById(R.id.addtopic);
    	  userinfo = (Button) findViewById(R.id.userinfo);
    	  Intent intent0 = this.getIntent();
    	  final Bundle bundle=intent0.getExtras();
  		  addtopic.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent(ClassNoteActivity.this,AddTopicActivity.class);//启动register活动
				intent.putExtra("bundle", bundle);
				startActivity(intent);
			}
		});
  		  userinfo.setOnClickListener(new OnClickListener(){
  			  public void onClick(View v){
  				  Intent intent1=new Intent(ClassNoteActivity.this,UserInfoActivity.class);
  				  intent1.putExtra("bundle", bundle);
  				  startActivity(intent1);
  			  }
  		  });
      }
      private void showlist(){//显示笔记列表
       	  final TopicDatabaseHelper topicdb=new TopicDatabaseHelper(this);
       	  Cursor c = topicdb.query("topic");
       	  String[] from = {"_id","title","note","conclusion"};
       	  int[] to = {R.id.id,R.id.title,R.id.note1,R.id.conclusion};
       	  //List<Map<String, String>> listItemsList=new ArrayList<Map<String,String>>();
       	  SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
       			  R.layout.topiclist,c,from,to);
       	  ListView listview = getListView();//列表视图
       	  listview.setAdapter(adapter);//添加适配器
         }
}
