package com.example.note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
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

public class NoteItemActivity extends Activity{
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
    	String test[] = {"","","","","","","","","","",""};
  	  super.onCreate(savedInstanceState);
  	  setContentView(R.layout.noteitemview);
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
  	  
  	  String Con = conclusion;
  	  int i = 0;
  	  
  	  while((Con.indexOf(';')!=-1)&&i<=10)
  	  {
  		  int temp = Con.indexOf(';');
  		  test[i] = Con.substring(0,temp+1);
  		  if(Con.length()!=temp+1)
  			  Con = Con.substring(temp+1);
  		  else
  			  break;
  		  i++;
  	  }
  	  String str = "<font color='black'><b>"+test[0]+"</b></font>"  + "<font color= 'red'><b>"+test[1]+"</b></font>"
  	  	  +"<font color= 'yellow'><b>"+test[2]+"</b></font>" + "<font color= 'blue'><b>"+test[3]+"</b></font>"
  	  	  +"<font color= 'maroon'><b>"+test[4]+"</b></font>" + "<font color= 'aqua'><b>"+test[5]+"</b></font>"
  	  	  +"<font color= 'gray'><b>"+test[6]+"</b></font>" + "<font color= 'purple'><b>"+test[7]+"</b></font>"
  	  	  +"<font color= 'olive'><b>"+test[8]+"</b></font>" + "<font color= 'lime'><b>"+test[9]+"</b></font>"
  	  	  +"<font color= 'navy'><b>"+test[10]+"</b></font>";
  	  
  	  TextView publisher =  (TextView)findViewById(R.id.publisher2);
	  publisher.setText(username);
	  TextView day =  (TextView)findViewById(R.id.date2);
  	  day.setText(date);
  	  TextView Title =  (TextView)findViewById(R.id.title2);
	  Title.setText(title);
	  TextView Note =  (TextView)findViewById(R.id.note2);
  	  Note.setText(note);
  	  TextView tv =  (TextView)findViewById(R.id.conclusion2);
  	  if(conclusion.indexOf(';')!=-1)
  		  tv.setText(Html.fromHtml(str));
  	  else{
  		  tv.setText(conclusion);
  		  tv.setTextColor(Color.parseColor("#000000"));
  	  }
  	  TextView par =  (TextView)findViewById(R.id.member2);
	  par.setText(member);
	  TextView place =  (TextView)findViewById(R.id.site2);
  	  place.setText(site);
  	  //showlist();
    }
    /*private void showlist(){
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
	}*/
}
