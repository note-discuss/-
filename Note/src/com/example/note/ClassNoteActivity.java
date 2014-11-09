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
import com.note.domain.Topic;
import com.note.service.TopicDatabaseHelper;
import com.note.service.UserService;
import com.example.note.R;
import com.example.note.AddTopicActivity;
import com.example.note.UserInfoActivity;
public class ClassNoteActivity extends ListActivity{
	  private   String processURL="http://172.17.133.231:8080/ServerProject/getTopicList.action?";
	  private final String processURL_findTopicList="http://172.17.133.231:8080/ServerProject/getTopicList.action?";
	  EditText note;
	  Button   addtopic;
	  Button   userinfo;
	  String userid;
      public void onCreate(Bundle savedInstanceState){
    	  super.onCreate(savedInstanceState);
    	  this.setTitle("课堂讨论笔记");
  		  setContentView(R.layout.note);
    	  Intent intent0 = this.getIntent();
    	  final Bundle bundle=intent0.getExtras();
    	  userid = bundle.getString("id");
  		  //showlist(bundle);
  		  findViews(bundle); 
  		  remote();
      }
      private void findViews(final Bundle bundle){
    	  addtopic = (Button) findViewById(R.id.addtopic);
    	  userinfo = (Button) findViewById(R.id.userinfo);
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
      }
      private void showlist(final Bundle bundle){//显示笔记列表
       	  final TopicDatabaseHelper topicdb=new TopicDatabaseHelper(this);
       	  Cursor c = topicdb.queryNote("topic");
       	  String[] from = {"id","title","note","conclusion"};
       	  int[] to = {R.id.id,R.id.title,R.id.note1,R.id.conclusion};
       	  //List<Map<String, String>> listItemsList=new ArrayList<Map<String,String>>();
       	  SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
       			  R.layout.topiclist,c,from,to);
       	  ListView listview = getListView();//列表视图
       	  listview.setAdapter(adapter);//添加适配器
         }
      private void remote(){
      	try{
    	    HttpClient httpclient = new DefaultHttpClient();
         	final String URL= processURL_findTopicList+"userid="+userid;
            HttpPost request=new HttpPost(URL);
    	    request.addHeader("Accept","text/json");
		    HttpResponse response =httpclient.execute(request);
		    HttpEntity entity=response.getEntity();
		    String json =EntityUtils.toString(entity,"UTF-8");
		    Log.d("mylog","json="+json);
		    if(json!=null){
		    	JSONArray jsonarray = new JSONArray(json);
		    	//List list = (List)JSONArray.toList(jsonarray, Topic.class);
		    }else{
		    	Log.d("mylog","json=null");
		    }
   	    } catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
      }
  	/*private List<Map<String, Object>> getData() {
    	try{
    	    HttpClient httpclient = new DefaultHttpClient();
         	final String URL= processURL_findTopicList+"userid="+userid;
            HttpPost request=new HttpPost(URL);
    	    request.addHeader("Accept","text/json");
		    HttpResponse response =httpclient.execute(request);
		    HttpEntity entity=response.getEntity();
		    String json =EntityUtils.toString(entity,"UTF-8");
		    Log.d("mylog",json);
		    if(json!=null){
		    	JSONArray jsonarray = new JSONArray(json);
		    	//List list = (List)JSONArray.toList(jsonarray, Topic.class);
		    }else{
		    	Log.d("mylog","json=null");
		    }
   	    } catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "G1");
		map.put("info", "google 1");
		map.put("img", R.drawable.i1);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G2");
		map.put("info", "google 2");
		map.put("img", R.drawable.i2);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		map.put("img", R.drawable.i3);
		list.add(map);
		
		return list;
	}*/
}
