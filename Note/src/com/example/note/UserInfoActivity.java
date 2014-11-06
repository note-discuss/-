package com.example.note;

import com.note.domain.User;
import com.note.service.TopicDatabaseHelper;
import com.note.service.UserDatabaseHelper;
import com.note.service.UserService;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.ListView;

public class UserInfoActivity extends ListActivity{
	 User user;
     public void onCreate(Bundle savedInstanceState){
    	 super.onCreate(savedInstanceState);
    	Intent intent = this.getIntent();
    	 Bundle bundle = intent.getExtras();
    	 String id = bundle.getString("id");
    	 if (id==null){
    		 Log.i("id is null!","_");  
    	 }
    	 else Log.i("TAG",id); 
    	 setContentView(R.layout.userinfo);
    	 showlist(id);
    	 findviews();
     }
     private void findviews(){
    	 Intent intent = this.getIntent();
    	 Bundle bundle = intent.getExtras();
    	 String id = bundle.getString("id");
    	 UserService userservice=new UserService(UserInfoActivity.this);
    	 
     }
     private void showlist(String id){//显示笔记列表
   	  final UserDatabaseHelper userdb=new UserDatabaseHelper(UserInfoActivity.this);
   	  Cursor c = userdb.queryUser("user",id);
   	  String[] from = {"_id","name","sex","role"};
   	  int[] to = {R.id.id,R.id.name,R.id.sex,R.id.role};
   	  SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
   			  R.layout.userinfolist,c,from,to);
   	  ListView listview = getListView();//列表视图
   	  listview.setAdapter(adapter);//添加适配器
     }
}