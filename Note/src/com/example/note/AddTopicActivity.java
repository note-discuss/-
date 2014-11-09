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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.note.domain.Topic;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class AddTopicActivity extends Activity{
    private static  String processURL="http://172.17.133.231:8080/ServerProject/addTopic.action?";
	private final String processURL_constant  = "http://172.17.133.231:8080/ServerProject/addTopic.action?";
	private final String processURL_findstring ="http://172.17.133.231:8080/ServerProject/jfindString.action?";
	String result=null;
	Button button;
	EditText title;
	EditText note;
	EditText conclusion;
	EditText site;
	EditText member;
	String res[]=null;
	ArrayAdapter<String> adapter;
	AutoCompleteTextView autoCompleteTextView;
	//private static final String[] phone = new String[] {"8611","8622","8633","8644"}; 
    public void onCreate(Bundle savedInstanceState){
  	  super.onCreate(savedInstanceState);
		  setContentView(R.layout.addtopic);
		 // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,phone);
		  autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.member);
		  autoCompleteTextView.setThreshold(1);
		  autoCompleteTextView.addTextChangedListener(new TextWatcher(){
			  public void onTextChanged(CharSequence s, int start, int before, int count){
	              String str = s.toString();     
                  String [] temp=getRemoteString(str);
                  adapter = new ArrayAdapter<String>(AddTopicActivity.this,  
	              android.R.layout.simple_dropdown_item_1line, temp); 
                  autoCompleteTextView.setAdapter(adapter);
			  }
		      public void beforeTextChanged(CharSequence s, int start, int count,  
		                int after) {  
		      }  
		      public void afterTextChanged(Editable s) {  
		      }  
		  });
		  findViews();  
    }
    private void findViews(){
   	   Intent intent = this.getIntent();
   	   Bundle bundle = intent.getExtras();
   	   final String idstr = bundle.getString("id");
       button = (Button)findViewById(R.id.publish);
  	   title  = (EditText)findViewById(R.id.title);
  	   note = (EditText)findViewById(R.id.note);
  	   conclusion= (EditText)findViewById(R.id.conclusion);
  	   site = (EditText)findViewById(R.id.site);
  	   member  = (EditText)findViewById(R.id.member);
  	   button.setOnClickListener(new OnClickListener(){
  		   public void onClick(View v){
  			   String titlestr=title.getText().toString().trim();
  			   String notestr=note.getText().toString();
  			   String  conclusionstr=conclusion.getText().toString();
  			   Log.d("mylog",conclusionstr);
  			   String sitestr=site.getText().toString().trim();
  			 String memberstr=member.getText().toString().trim();
  			 addTopicRemoteService(titlestr,notestr,conclusionstr,idstr,sitestr,memberstr);
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
    private String[] getRemoteString(String prefix){
    	try{
    	    HttpClient httpclient = new DefaultHttpClient();
         	final String URL= processURL_findstring+"prefix="+prefix;
            HttpPost request=new HttpPost(URL);
    	    request.addHeader("Accept","text/json");
		    HttpResponse response =httpclient.execute(request);
		    HttpEntity entity=response.getEntity();
		    String json =EntityUtils.toString(entity,"UTF-8");
		    if(json!=null){
				JSONObject jsonObject=new JSONObject(json);
				result=jsonObject.get("message").toString().trim();
				res=result.split("\\)");
				int size=res.length;
				String len=Integer.toString(size);
		    }else{
		    	Log.d("mylog","json=null");
		    }
		    for(int j=0;j<res.length;++j){
		    	res[j]=res[j]+");";
		    }
   	    } catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return res;
    }
	private void addTopicRemoteService(String title,String note,
			 String conclusion,String userid,String site,String member){
    	try {
   		    String date=getDate();
   		    date=java.net.URLEncoder.encode(date,"utf-8");
   		    title=java.net.URLEncoder.encode(title,"utf-8");
   		    note=java.net.URLEncoder.encode(note,"utf-8");
   		    conclusion=java.net.URLEncoder.encode(conclusion,"utf-8");
   		    site=java.net.URLEncoder.encode(site,"utf-8");
	    	HttpClient httpclient = new DefaultHttpClient();
	    	processURL= processURL_constant+"title="+title+"&note="+note+
	    			"&conclusion="+conclusion+"&date="+date+"&userid="+userid
	    			+"&site="+site+"&member="+member;
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
			 AlertDialog.Builder builder=new Builder(AddTopicActivity.this);
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
