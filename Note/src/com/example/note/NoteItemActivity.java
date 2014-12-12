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
  	  Log.d("mylog","In NoteItemActivity note="+note);
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
  		  if(i==0)
  			  Con = Con+";";
  		  int temp = Con.indexOf(';');
  		  test[i] = Con.substring(0,temp+1);
  		  if(Con.length()!=temp+1)
  			  Con = Con.substring(temp+1);
  		  else
  			  break;
  		  i++;
  	  }
  	  i = 0;
  	  
  	  String shiyan = "";
  	  String tmp1 = "";
  	  String tmp2 = "";
  	  while(i<=10&&test[i]!="")
  	  {
  		  if(i!=0){
  			  shiyan = shiyan + "<br/>";
  			  tmp1 = test[i].substring(0,test[i].indexOf('('));
  			  tmp2 = test[i].substring(test[i].indexOf('('),test[i].length()-1);
  		  }
  		  switch(i)
  		  {
  		  case 0:
  			  shiyan = shiyan + "<font color='black'>";
  			  shiyan = shiyan + test[0];
  			  shiyan = shiyan + "</font>";
  			  break;
  		  case 1:
  			  shiyan = shiyan + "<font color='red'>";
			  shiyan = shiyan + tmp1;
			  shiyan = shiyan + "</font><br/>";
			  shiyan = shiyan + "<font color='red'>";
			  shiyan = shiyan + tmp2;
			  shiyan = shiyan + "</font>";
			  break;
  		  case 2:
  			  shiyan = shiyan + "<font color='yellow'>";
			  shiyan = shiyan + tmp1;
			  shiyan = shiyan + "</font><br/>";
			  shiyan = shiyan + "<font color='yellow'>";
			  shiyan = shiyan + tmp2;
			  shiyan = shiyan + "</font>";
			  break;
  		  case 3:
  			  shiyan = shiyan + "<font color='blue'>";
			  shiyan = shiyan + tmp1;
			  shiyan = shiyan + "</font><br/>";
			  shiyan = shiyan + "<font color='blue'>";
			  shiyan = shiyan + tmp2;
			  shiyan = shiyan + "</font>";
			  break;
  		  case 4:
  			  shiyan = shiyan + "<font color='maroon'>";
			  shiyan = shiyan + tmp1;
			  shiyan = shiyan + "</font><br/>";
			  shiyan = shiyan + "<font color='maroon'>";
			  shiyan = shiyan + tmp2;
			  shiyan = shiyan + "</font>";
			  break;
  		  case 5:
  			  shiyan = shiyan + "<font color='aqua'>";
			  shiyan = shiyan + tmp1;
			  shiyan = shiyan + "</font><br/>";
			  shiyan = shiyan + "<font color='aqua'>";
			  shiyan = shiyan + tmp2;
			  shiyan = shiyan + "</font>";
			  break;
  		  case 6:
  			  shiyan = shiyan + "<font color='gray'>";
			  shiyan = shiyan + tmp1;
			  shiyan = shiyan + "</font><br/>";
			  shiyan = shiyan + "<font color='gray'>";
			  shiyan = shiyan + tmp2;
			  shiyan = shiyan + "</font>";
			  break;
  		  case 7:
  			  shiyan = shiyan + "<font color='purple'>";
			  shiyan = shiyan + tmp1;
			  shiyan = shiyan + "</font><br/>";
			  shiyan = shiyan + "<font color='purple'>";
			  shiyan = shiyan + tmp2;
			  shiyan = shiyan + "</font>";
			  break;
  		  case 8:
  			  shiyan = shiyan + "<font color='olive'>";
			  shiyan = shiyan + tmp1;
			  shiyan = shiyan + "</font><br/>";
			  shiyan = shiyan + "<font color='olive'>";
			  shiyan = shiyan + tmp2;
			  shiyan = shiyan + "</font>";
			  break;
  		  case 9:
  			  shiyan = shiyan + "<font color='lime'>";
			  shiyan = shiyan + tmp1;
			  shiyan = shiyan + "</font><br/>";
			  shiyan = shiyan + "<font color='lime'>";
			  shiyan = shiyan + tmp2;
			  shiyan = shiyan + "</font>";
			  break;
  		  case 10:
  			  shiyan = shiyan + "<font color='navy'>";
			  shiyan = shiyan + tmp1;
			  shiyan = shiyan + "</font><br/>";
			  shiyan = shiyan + "<font color='navy'>";
			  shiyan = shiyan + tmp2;
			  shiyan = shiyan + "</font>";
			  break;
  		  default:
				  break;
  		  }
  		  i++;
  	  }
  	  /*System.out.println("shiyan="+shiyan);
  	  String str = "<font color='black'><b>"+test[0]+"</b></font>"  + "<font color= 'red'><b>"+test[1]+"</b></font>"
  	  	  +"<font color= 'yellow'><b>"+test[2]+"</b></font>" + "<font color= 'blue'><b>"+test[3]+"</b></font><br/>"
  	  	  +"<font color= 'maroon'><b>"+test[4]+"</b></font><br/>" + "<font color= 'aqua'><b>"+test[5]+"</b></font><br/>"
  	  	  +"<font color= 'gray'><b>"+test[6]+"</b></font><br/>" + "<font color= 'purple'><b>"+test[7]+"</b></font><br/>"
  	  	  +"<font color= 'olive'><b>"+test[8]+"</b></font><br/>" + "<font color= 'lime'><b>"+test[9]+"</b></font><br/>"
  	  	  +"<font color= 'navy'><b>"+test[10]+"</b></font><br/>";
  	  System.out.println("str="+str);*/
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
  		  tv.setText(Html.fromHtml(shiyan));
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
     	  listview.setAdapter(adapter);//Ìí¼ÓÊÊÅäÆ÷
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