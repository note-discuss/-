package com.example.note;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import net.sf.json.JSONArray;






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

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.note.R;
import com.note.domain.Topic;
import com.note.service.TopicDatabaseHelper;
import com.note.service.UserService;
import com.note.service.remoteURL;
import com.example.note.R;
import com.example.note.AddTopicActivity;
import com.example.note.UserInfoActivity;
public class ClassNoteActivity extends ListActivity{
	  static remoteURL remote = new remoteURL();
	  private   String processURL=remote.remoteURL+"getTopicList.action?";
	  private final String processURL_findTopicList=remote.remoteURL+"getTopicList.action?";
	  private final String processURL_myTopicList=remote.remoteURL+"myTopicList.action?";
	  EditText searchtext;
	  Button   addtopic;
	  Button   userinfo;
	  Button search;
	  String userid;
	  String result=null;
      public void onCreate(Bundle savedInstanceState){
    	  super.onCreate(savedInstanceState);
    	  this.setTitle("课堂讨论笔记");
  		  setContentView(R.layout.note);
    	  Intent intent0 = this.getIntent();
    	  final Bundle bundle=intent0.getExtras();
    	  userid = bundle.getString("id");
    	  final String URL= processURL_findTopicList+"userid="+userid;
  		  findViews(bundle); 
  		  remote(URL);
      }
      private void findViews(final Bundle bundle){
    	  addtopic = (Button) findViewById(R.id.addtopic);
    	  userinfo = (Button) findViewById(R.id.userinfo);
    	  searchtext = (EditText) findViewById(R.id.searchtext);
    	  search = (Button) findViewById(R.id.search);
  		  addtopic.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent(ClassNoteActivity.this,AddTopicActivity.class);//启动register活动
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
  		  userinfo.setOnClickListener(new OnClickListener(){
  			  public void onClick(View v){
  				  Intent intent1=new Intent(ClassNoteActivity.this,UserInfoActivity.class);
  				  intent1.putExtras(bundle);
  				  startActivity(intent1);
  			  }
  		  });
  		 search.setOnClickListener(new OnClickListener(){
  			  public void onClick(View v){
  				  String searchstr = searchtext.getText().toString().trim();
  				  String URL_myTopic=processURL_myTopicList+"searchstr="+searchstr;
  				  remote(URL_myTopic);
  			  }
  		  });
      }
      private void showlist(ArrayList<Topic> list){//显示笔记列表
       	  String[] from = {"title","note","date"};
       	  int[] to = {R.id.title,R.id.note,R.id.date};
       	  SimpleAdapter adapter = new SimpleAdapter(this,
       			  getData(list),R.layout.topiclist,from,to);
       	  
       	  ListView listview = getListView();
       	  listview.setAdapter(adapter);//添加适配器
       	  listview.setOnItemClickListener(new OnItemClickListener(){
       		  public void onItemClick(AdapterView<?> parent,View view,int position,long id){
       			    //Log.i("mylog", "item class : "+ ((ListView) parent).
       			     //getItemAtPosition(position).getClass().getSimpleName());
       		          HashMap<String, String>  map = (HashMap<String, String>) ((ListView) parent).
       				 getItemAtPosition(position);  
       		         String  topic_id=map.get("id");
       		         Intent notelist = new Intent(ClassNoteActivity.this,NoteListActivity.class);
       		         Bundle notebundle = new Bundle();
       		         notebundle.putString("topic_id", topic_id);
       		         notebundle.putString("userid", userid);
       		         notelist.putExtras(notebundle);
       		         startActivity(notelist);
       		  }
       	  });
         }
      private void remote(String url){
      	try{
    	    HttpClient httpclient = new DefaultHttpClient();
            HttpPost request=new HttpPost(url);
    	    request.addHeader("Accept","text/json");
		    HttpResponse response =httpclient.execute(request);
		    HttpEntity entity=response.getEntity();
		    String json =EntityUtils.toString(entity,"UTF-8");
		    if(json!=null){
				JSONObject jsonObject=new JSONObject(json);
				result=jsonObject.get("TopicList").toString().trim();
		    	JSONArray jsonarray = new JSONArray(result);
		    	int len = jsonarray.length();
		    	ArrayList<Topic> list = new ArrayList<Topic>();
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
		            Topic topic = new Topic(userid,title,note,conclusion,date,site
		            		,member,id);
		            list.add(topic);
		    	}
		    	showlist(list);
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
  	  private List<Map<String, Object>> getData(ArrayList<Topic> list1) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int len = list1.size();
        
        for(int i=0;i<len;++i){
        	Map<String, Object> map = new HashMap<String, Object>();
        	map.put("title", list1.get(i).getTitle());        	
        	String note = list1.get(i).getNote();
        	if(note.length()>100) note=note.substring(0, 99)+"……";
        	map.put("note", note);
        	map.put("date", list1.get(i).getDate());
        	map.put("id", list1.get(i).getId());
        	list.add(map);
        }
        Log.d("mylog","return map");
		return list;
	}
}