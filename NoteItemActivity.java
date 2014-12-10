package com.example.note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.note.domain.Note;
import com.note.domain.Topic;
import com.note.service.remoteURL;

public class NoteItemActivity extends ListActivity{
	  static remoteURL remote = new remoteURL();
	  private   String processURL=remote.remoteURL+"getTopic.action?";
	  private   String addconflictURL=remote.remoteURL+"addConflict.action?";
	  private final String processURL_findNoteList=remote.remoteURL+"getNoteList.action?";
	  String topicid;
	  String noteid;
	  String conclusion;
	  String title;
	  String note;
	  String site;
	  String date;
	  String member;
	  String username;
	  Bundle bundle;
	  Intent intent;
	  String result;
	  String myid;
    public void onCreate(Bundle savedInstanceState){
  	  super.onCreate(savedInstanceState);
  	  setContentView(R.layout.conflict);
  	  intent = this.getIntent();
  	  bundle = intent.getExtras();
  	  topicid=bundle.getString("topicid");
  	  noteid=bundle.getString("noteid");
  	  conclusion=bundle.getString("conflict");
  	  title=bundle.getString("title");
  	  note=bundle.getString("note");
  	  site=bundle.getString("site");
  	  date=bundle.getString("date");
  	  member=bundle.getString("member");
  	  username=bundle.getString("username");
  	  myid=bundle.getString("userid");
  	  String URL=processURL+"topicid="+topicid;
  	  showlist();
    }
    private void showlist(){
     	  String[] from = {"title","note","conclusion","member","site","username","date","topicid","id"};
     	  int[] to = {R.id.title2,R.id.note2,R.id.conclusion2,R.id.member2,R.id.site2,R.id.publisher2,
     			  R.id.date2};
     	  SimpleAdapter adapter = new SimpleAdapter(this,
     			  getData(),R.layout.noteitemview,from,to);
     	  ListView listview = getListView();
     	  listview.setAdapter(adapter);//ÃÌº”  ≈‰∆˜
    }
	  private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
      	Map<String, Object> map = new HashMap<String, Object>();
      	map.put("title", title);        	
      	map.put("note", note);
      	map.put("date", date);
      	map.put("topicid", topicid);
      	map.put("id", noteid);
      	map.put("member", member);
      	map.put("userid", myid);
      	map.put("conclusion", conclusion);
      	map.put("site",site);
      	map.put("username", username);
      	list.add(map);
		return list;
	}
}
