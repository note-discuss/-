package com.example.note;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class NoteListActivity extends ListActivity{
	  static remoteURL remote = new remoteURL();
	  private   String processURL=remote.remoteURL+"getTopic.action?";
	  private final String processURL_findNoteList=remote.remoteURL+"getNoteList.action?";
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
    			  startActivity(intent);
    		  }
    	  });
      }
      private void showlist(Topic topic,ArrayList<Note> list1){
    	  if(list1==null) Log.d("mylog","in showlist list = null!");
    	  Log.d("mylog","in showlist topicid="+topic.getId());
       	  String[] from = {"title","note","conclusion","member","site","userid","date"};
       	  int[] to = {R.id.title1,R.id.note1,R.id.conclusion1,R.id.member1,R.id.site1,R.id.publisher1,R.id.date1};
       	  SimpleAdapter adapter = new SimpleAdapter(this,
       			  getData(topic,list1),R.layout.noteitem_list,from,to);
       	  ListView listview = getListView();
       	  listview.setAdapter(adapter);//添加适配器
      }
  	  private List<Map<String, Object>> getData(Topic topic,ArrayList<Note> list1) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        	Map<String, Object> map = new HashMap<String, Object>();
        	map.put("title", topic.getTitle());        	
        	map.put("note", topic.getNote());
        	map.put("date", topic.getDate());
        	map.put("id", topic.getId());
        	map.put("member", topic.getMember());
        	map.put("userid", topic.getUserid());
        	map.put("conclusion", topic.getConclusion());
        	map.put("site",topic.getSite());
        	list.add(map);
        	for(int i=0;i<list1.size();++i){
        		Map<String, Object> map1 = new HashMap<String, Object>();
        		map1.put("title", list1.get(i).getTitle());
        		map1.put("note", list1.get(i).getNote());
            	map1.put("date", list1.get(i).getDate());
            	map1.put("id", list1.get(i).getId());
            	map1.put("member", list1.get(i).getMember());
            	map1.put("userid", list1.get(i).getUserid());
            	map1.put("conclusion", list1.get(i).getConclusion());
            	map1.put("site", list1.get(i).getSite());
            	list.add(map1);
        	}
        	Log.d("mylog","list size = "+list.size());
		return list;
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
