package com.example.note;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import com.note.service.remoteURL;

public class AddNoteActivity extends Activity {
	static remoteURL remote = new remoteURL();
    private static  String processURL=remote.remoteURL+"addNote.action?";
	private final String processURL_constant  = remote.remoteURL+"addNote.action?";
	private final String processURL_findstring =remote.remoteURL+"jfindString.action?";
	String result=null;
	String publisher1;
	String topicid1;
	Button publish;
	EditText title;
	EditText note;
	EditText conclusion;
	EditText site;
	EditText member;
	ArrayAdapter<String> adapter;
	MultiAutoCompleteTextView multiautoCompleteTextView;
    public void onCreate(Bundle savedInstanceState){
    	  super.onCreate(savedInstanceState);
  		  setContentView(R.layout.addnote);
  		  Intent intent = this.getIntent();
  		  Bundle bundle = intent.getExtras();
  		  publisher1=bundle.getString("publisher");
  		  topicid1=bundle.getString("topicid");
  		  Log.d("mylog","topicid ="+topicid1+"publisher="+publisher1);
		  multiautoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.member2);
		  multiautoCompleteTextView.setThreshold(1);
		  multiautoCompleteTextView.addTextChangedListener(new TextWatcher(){
			  public void onTextChanged(CharSequence s, int start, int before, int count){
	              String str = s.toString();     
	              int len = s.length();
	              if(len!=0){
	            	if(str.charAt(len-1)!=' '){
	            		String [] tmp=str.split(", ");
	                    String [] temp=remote.getRemoteString(processURL_findstring,tmp[tmp.length-1]);
	                    if(temp!=null){
	                    adapter = new ArrayAdapter<String>(AddNoteActivity.this,  
	  	                android.R.layout.simple_dropdown_item_1line, temp); 
	                    multiautoCompleteTextView.setAdapter(adapter);
	                    Log.d("mylog","multiauto");
	                    multiautoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
	            	}
	              }
                  }
			  }
		      public void beforeTextChanged(CharSequence s, int start, int count,  
		                int after) {  
		      }  
		      public void afterTextChanged(Editable s) {  
		      }  
		  });
		  findview();
    }
    public void findview(){
    	publish = (Button)findViewById(R.id.publish2);
    	title = (EditText)findViewById(R.id.title2);
    	note = (EditText)findViewById(R.id.note2);
    	conclusion = (EditText)findViewById(R.id.conclusion2);
    	site =(EditText)findViewById(R.id.site2);
    	member = (EditText)findViewById(R.id.member2);
   	    publish.setOnClickListener(new OnClickListener(){
  		   public void onClick(View v){
  			   String titlestr=title.getText().toString().trim();
  			   String notestr=note.getText().toString();
  			   String  conclusionstr=conclusion.getText().toString();
  			   String sitestr=site.getText().toString().trim();
  			 String memberstr=member.getText().toString().trim();
  			 addNoteRemoteService(titlestr,notestr,conclusionstr,sitestr,memberstr);
               /*TopicService topicservice = new TopicService(AddTopicActivity.this);
               Topic topic = new Topic(userid,titlestr,notestr,conclusionstr);
               boolean f = topicservice.insert(topic);
               if(f){
            	   Log.i("TAG","发布成功!");  
            	   Toast.makeText(AddTopicActivity.this, "发布成功！", Toast.LENGTH_LONG).show();
               }*/
  		   }
  	   });
    }
    public void addNoteRemoteService(String title,String note,
			 String conclusion,String site,String member){
    	try {
   		    String date=getDate();
   		    date=java.net.URLEncoder.encode(date,"utf-8");
   		    title=java.net.URLEncoder.encode(title,"utf-8");
   		    note=java.net.URLEncoder.encode(note,"utf-8");
   		    conclusion=java.net.URLEncoder.encode(conclusion,"utf-8");
   		    site=java.net.URLEncoder.encode(site,"utf-8");
   		    String userid=java.net.URLEncoder.encode(publisher1,"utf-8");
   		    String topicid=java.net.URLEncoder.encode(topicid1,"utf-8");
   		    Log.d("mylog","userid="+userid);
	    	HttpClient httpclient = new DefaultHttpClient();
	    	Log.d("mylog","HttpClient");
	    	processURL= processURL_constant+"title="+title+"&note="+note+
	    			"&conclusion="+conclusion+"&date="+date+"&site="+site+
	    			"&member="+member+"&topicid="+topicid+"&userid="+userid;
	    	Log.d("mylog","processURL="+processURL);
	    	HttpPost request=new HttpPost(processURL);
	    	request.addHeader("Accept","text/json");
			HttpResponse response =httpclient.execute(request);
			HttpEntity entity=response.getEntity();
			String json =EntityUtils.toString(entity,"UTF-8");
			if(json!=null){
				JSONObject jsonObject=new JSONObject(json);
				result=jsonObject.get("message").toString().trim();
			}
		   if(result==null){ 
			   json="通信出错！";
		   }
			//创建提示框提醒是否登录成功
		   Log.d("mylog","AlertDialog");
			 AlertDialog.Builder builder=new Builder(AddNoteActivity.this);
			 builder.setTitle("提示")
			 .setMessage(result)
			 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			}).create().show();
		 
    	 } catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	private String getDate(){
		java.util.Date date = new java.util.Date();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String res = df.format(date);
		return res;
	}
}
