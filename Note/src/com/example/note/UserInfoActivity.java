package com.example.note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.note.domain.Note;
import com.note.domain.Topic;
import com.note.domain.User;
import com.note.service.TopicDatabaseHelper;
import com.note.service.UserDatabaseHelper;
import com.note.service.UserService;
import com.note.service.remoteURL;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class UserInfoActivity extends ListActivity{
	 static remoteURL remote = new remoteURL();
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
     }
     private void showlist(String id){
      ArrayList<User> list= remote.getRemoteUser(id);
   	  if(list==null) Log.d("mylog","in showlist list = null!");
   	      String[] from = {"id","name","sex","role"};
   	      int[] to = {R.id.id,R.id.name,R.id.sex,R.id.role};
      	  SimpleAdapter adapter = new SimpleAdapter(this,
      			  getData(list),R.layout.userinfolist,from,to);
      	  ListView listview = getListView();
      	  listview.setAdapter(adapter);//ÃÌº”  ≈‰∆˜
     }
 	  private List<Map<String, Object>> getData(ArrayList<User> list1) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
       	Map<String, Object> map = new HashMap<String, Object>();
       	map.put("id", list1.get(0).getId());        	
       	map.put("name", list1.get(0).getUsername());
       	map.put("sex", list1.get(0).getSex());
       	map.put("role", list1.get(0).getRole());
       	list.add(map);
		return list;
	}
}