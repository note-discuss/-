package com.example.note;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.note.domain.Note;
import com.note.domain.Topic;
import com.note.service.remoteURL;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class NoteListActivity extends ListActivity{
	  static remoteURL remote = new remoteURL();
	  private   String processURL=remote.remoteURL+"getTopic.action?";
	  private   String addconflictURL=remote.remoteURL+"addConflict.action?";
	  private final String processURL_findNoteList=remote.remoteURL+"getNoteList.action?";
	  //private int[] colors = new int[]{0x30FFFFFF, 0x30EEEEFF};
	  String topicid;
	  Bundle bundle;
	  Intent intent;
	  String result;
	  String myid;
	  Button addnote;
      public void onCreate(Bundle savedInstanceState){
    	  super.onCreate(savedInstanceState);
    	  setContentView(R.layout.noteitem);
    	  intent = this.getIntent();
    	  bundle = intent.getExtras();
    	  topicid=bundle.getString("topic_id");
    	  myid=bundle.getString("userid");
    	  String URL=processURL+"topicid="+topicid;
    	  remote(URL);
    	  findview();
      }
      private void findview(){
    	  addnote = (Button) findViewById(R.id.addnote1);
    	  addnote.setOnClickListener(new OnClickListener(){
    		  public void onClick(View v){
    			  Intent intent = new Intent(NoteListActivity.this,AddNoteActivity.class);
    			  Bundle bundle = new Bundle();
    			  bundle.putString("publisher",myid);
    			  bundle.putString("topicid", topicid);
    			  intent.putExtras(bundle);
    			  startActivityForResult(intent,1);
    		  }
    	  });
      }
      public void onActivityResult(int requestCode, int resultCode, Intent data){
    	  super.onActivityResult(requestCode, resultCode, data);
    	  String URL=processURL+"topicid="+topicid;
    	  remote(URL); 
      }
      private void showlist(Topic topic,ArrayList<Note> list1){
    	  if(list1==null) Log.d("mylog","in showlist list = null!");
    	  Log.d("mylog","in showlist topicid="+topic.getId());
       	  String[] from = {"title","note","conclusion","member","site","username","date","topicid","id"};
       	  int[] to = {R.id.title1,R.id.note1,R.id.conclusion1,R.id.member1,R.id.site1,R.id.publisher1,
       			  R.id.date1,R.id.topicid,R.id.noteid};
       	  
       	  //TextView tv = (TextView)findViewById(R.id.note1);
       	  //String str1 = "<font color=\"#ffff00\">456,<font color=\"#ffff00\">";
       	  //tv.setText(Html.fromHtml(str1));
       	  SimpleAdapter adapter = new SimpleAdapter(this,
       			  getData(topic,list1),R.layout.noteitem_list,from,to){
       		  public View getView(int position,View convertView,ViewGroup parent){
       			  final int p=position;
       			  convertView = LayoutInflater.from(getBaseContext()).inflate(R.layout.noteitem_list,parent,false); 
       			  TextView lblName= (TextView)convertView.findViewById(R.id.note1); 
       			  lblName.setTextColor(Color.RED);
       			  final View view=super.getView(position,convertView,parent);
       			//int colorPos = position%colors.length;  
       	        //view.setBackgroundColor(colors[colorPos]);
       			  Button addconflict=(Button)view.findViewById(R.id.addconflict);
       			  addconflict.setOnClickListener(new OnClickListener(){
       				  public void onClick(View v){
       					TextView note_id=(TextView)view.findViewById(R.id.noteid);
       					TextView topic_id=(TextView)view.findViewById(R.id.topicid);
       					TextView conclusion=(TextView)view.findViewById(R.id.conclusion1);
       					EditText conflict=(EditText)view.findViewById(R.id.conflict);
       					String conflictstr=conflict.getText().toString();
       					String topicid=topic_id.getText().toString();
       					String noteid=note_id.getText().toString();
       					String conclusionstr=conclusion.getText().toString();
       					//Toast.makeText(NoteListActivity.this, noteid, Toast.LENGTH_LONG).show();
       					boolean f=addRemoteConflict(conclusionstr,conflictstr,topicid,noteid);
       					if(f){
       			    	  String URL=processURL+"topicid="+topicid;
       			    	  remote(URL);
       					}
       					else{
       						Toast.makeText(NoteListActivity.this, "通信出错！", Toast.LENGTH_LONG).show();
       					}
       				  }
       			  });
       			  return view;
       		  }
       	  };
       	  ListView listview = getListView();
       	  listview.setAdapter(adapter);//添加适配器
      }
  	  private List<Map<String, Object>> getData(Topic topic,ArrayList<Note> list1) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        	Map<String, Object> map = new HashMap<String, Object>();
        	map.put("title", topic.getTitle()); 
        	map.put("note", topic.getNote());
        	map.put("date", topic.getDate());
        	map.put("topicid", topic.getId());
        	map.put("id", "-1");
        	map.put("member", topic.getMember());
        	map.put("userid", topic.getUserid());
        	map.put("conclusion", topic.getConclusion());
        	map.put("site",topic.getSite());
        	String name=remote.getRemoteUserName(topic.getUserid());
        	Log.d("mylog","username="+name);
        	map.put("username", name);
        	list.add(map);
        	
        	for(int i=0;i<list1.size();++i){
        		Map<String, Object> map1 = new HashMap<String, Object>();
        		map1.put("title", list1.get(i).getTitle());
        		map1.put("topicid", list1.get(i).getTopicid());
        		map1.put("note", list1.get(i).getNote());
            	map1.put("date", list1.get(i).getDate());
            	map1.put("id", list1.get(i).getId());
            	map1.put("member", list1.get(i).getMember());
            	map1.put("userid", list1.get(i).getUserid());
            	map1.put("conclusion", list1.get(i).getConclusion());
            	map1.put("site", list1.get(i).getSite());
            	name=remote.getRemoteUserName(list1.get(i).getUserid());
            	map1.put("username", name);
            	list.add(map1);
        	}
        	Log.d("mylog","list size = "+list.size());
		return list;
	}
      private boolean addRemoteConflict(String conclusion,String conflict,String topicid,String noteid){//给远端发送新加矛盾的noteid,topicid,矛盾，以及结论
      	try{
    	    HttpClient httpclient = new DefaultHttpClient();
    	    conclusion=conclusion+";"+conflict+"\\("+myid+"\\)";
    	    conclusion=java.net.URLEncoder.encode(conclusion,"utf-8");
    	    topicid=java.net.URLEncoder.encode(topicid,"utf-8");
    	    noteid=java.net.URLEncoder.encode(noteid,"utf-8");
    	    String url=addconflictURL+"conclusion="+conclusion+"&topicid="+topicid+"&noteid="+noteid;
            HttpPost request=new HttpPost(url);
    	    request.addHeader("Accept","text/json");
		    HttpResponse response =httpclient.execute(request);
		    HttpEntity entity=response.getEntity();
		    String json =EntityUtils.toString(entity,"UTF-8");
		    if(json!=null){
				JSONObject jsonObject=new JSONObject(json);
				result=jsonObject.get("message").toString().trim();
                if(result.equals("true")) return true;
                else return false;
		    	}
		} catch (IOException e) {
			e.printStackTrace();
		} catch(JSONException e){
			e.printStackTrace();
		}
      	return false;
    }
      private void remote(String url){//获得当前主题信息
        	try{
      	    HttpClient httpclient = new DefaultHttpClient();
            HttpPost request=new HttpPost(url);
      	    request.addHeader("Accept","text/json");
  		    HttpResponse response =httpclient.execute(request);
  		    HttpEntity entity=response.getEntity();
  		    String json =EntityUtils.toString(entity,"UTF-8");
  		    if(json!=null){
				JSONObject jsonObject=new JSONObject(json);
				result=jsonObject.get("Topic").toString().trim();
		    	JSONArray jsonarray = new JSONArray(result);
		    	JSONObject obj = jsonarray.getJSONObject(0);
  		            String title = obj.getString("title");
  		            String member= obj.getString("member");
  		            String site  = obj.getString("site");
  		            String date = obj.getString("date");
  		            String note = obj.getString("note");
  		            String conclusion = obj.getString("conclusion");
  		            String userid = obj.getString("userid");
  		            String id= obj.getString("id");
  		            Topic topic = new Topic(userid,title,note,conclusion,date,site
  		            		,member,id);
  		            String NOTEURL=processURL_findNoteList+"&topicid="+id;
  		            remoteNote(topic,NOTEURL);
  		    	}
  		} catch (IOException e) {
  			e.printStackTrace();
  		} catch(JSONException e){
  			e.printStackTrace();
  		}
      }
      private void remoteNote(Topic topic,String url){//获得该主题的所有笔记并传给show
        	try{
      	    HttpClient httpclient = new DefaultHttpClient();
            HttpPost request=new HttpPost(url);
      	    request.addHeader("Accept","text/json");
  		    HttpResponse response =httpclient.execute(request);
  		    HttpEntity entity=response.getEntity();
  		    String json =EntityUtils.toString(entity,"UTF-8");
  		    Log.d("mylog","json="+json);
  		    ArrayList<Note> list =new ArrayList<Note>();
  		    if(json!=null){
  		    	if(!(json.equals("{NoteList=list==null!}"))){
  				JSONObject jsonObject=new JSONObject(json);
  				Log.d("mylog","jsonobject");
  				result=jsonObject.get("NoteList").toString().trim();
  				Log.d("mylog","result");
  		    	    JSONArray jsonarray = new JSONArray(result);
  		    	    int len = jsonarray.length();
  		    	    for(int i=0;i<len;++i){
  		                JSONObject obj = jsonarray.getJSONObject(i);
  		                String title = obj.getString("title");
  		                String member= obj.getString("member");
  		                String site  = obj.getString("site");
  		                String date = obj.getString("date");
  		                String note = obj.getString("note");
  		                
  		                String conclusion = obj.getString("conclusion");
  		                String userid = obj.getString("userid");
  		                String id= obj.getString("id");
  		                Note note1 = new Note(userid,title,note,conclusion,date,site
  		            		,member,id,topic.getId());
  		                list.add(note1);
  		        	}
  		    	}
  		    	if(list==null) Log.d("mylog","in remoteNote list = null!");
  		    	Log.d("mylog","showlist");
  		    	showlist(topic,list);
  		    }else{
  		    	Log.d("mylog","json=null");
  		    }
     	    } catch (ClientProtocolException e) {
  			e.printStackTrace();
  		} catch (IOException e) {
  			e.printStackTrace();
  		} catch(JSONException e){
  			e.printStackTrace();
  		}
        }
}
