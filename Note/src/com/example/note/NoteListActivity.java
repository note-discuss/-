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
	  private final String processURL_findTopicList=remote.remoteURL+"getTopic.action?";
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
      private void showTopic(Topic topic){
       	  String[] from = {"title","note","conclusion","member","site","userid","date"};
       	  int[] to = {R.id.title1,R.id.note1,R.id.conclusion1,R.id.member1,R.id.site1,R.id.publisher1,R.id.date1};
       	  SimpleAdapter adapter = new SimpleAdapter(this,
       			  getData(topic),R.layout.noteitem_list,from,to);
       	  ListView listview = getListView();
       	  listview.setAdapter(adapter);//ÃÌº”  ≈‰∆˜
      }
  	  private List<Map<String, Object>> getData(Topic topic) {
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
		return list;
	}
      private void remote(String url){
        	try{
      	    HttpClient httpclient = new DefaultHttpClient();
            HttpPost request=new HttpPost(url);
      	    request.addHeader("Accept","text/json");
  		    HttpResponse response =httpclient.execute(request);
  		    HttpEntity entity=response.getEntity();
  		    String json =EntityUtils.toString(entity,"UTF-8");
  		    Log.d("mylog","json="+json);
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
  		            Log.d("mylog","Topic="+userid+title+note+conclusion+date+site
  		            		+member+id);
  		            showTopic(topic);
  		    	}
  		} catch (IOException e) {
  			e.printStackTrace();
  		} catch(JSONException e){
  			e.printStackTrace();
  		}
      }
}
