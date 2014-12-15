package com.example.note;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NoteListActivity extends ListActivity{
	  static remoteURL remote = new remoteURL();
	  private   String processURL=remote.remoteURL+"getTopic.action?";
	  private   String addconflictURL=remote.remoteURL+"addConflict.action?";
	  private final String processURL_findNoteList=remote.remoteURL+"getNoteList.action?";
	  private final String processURL_deleteNote=remote.remoteURL+"deleteNote.action?";
	  String topicid;
	  Bundle bundle;
	  Intent intent;
	  String result;
	  String myid;
	  Button addnote;
	  Button addmember;
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
       	  String[] from = {"title","note","conclusion","conclusion1","member","site","username","date","topicid","id"};
       	  int[] to = {R.id.title1,R.id.note1,R.id.conclusion1,R.id.clandcf,R.id.member1,R.id.site1,R.id.publisher1,
       			  R.id.date1,R.id.topicid,R.id.noteid};
       	  SimpleAdapter adapter = new SimpleAdapter(this,
       			  getData(topic,list1),R.layout.noteitem_list,from,to){
       		  public View getView(int position,View convertView,ViewGroup parent){
       			  final int p=position;
       			  final View view=super.getView(position,convertView,parent);
 				  final TextView note_id=(TextView)view.findViewById(R.id.noteid);
 				  final TextView topic_id=(TextView)view.findViewById(R.id.topicid);
 				  final TextView title =(TextView)view.findViewById(R.id.title1);
 				  final TextView note =(TextView)view.findViewById(R.id.note1);
 				  final TextView conclusion=(TextView)view.findViewById(R.id.conclusion1);
 				  final TextView member=(TextView)view.findViewById(R.id.member1);
 				  final TextView site=(TextView)view.findViewById(R.id.site1);
 				  final TextView date=(TextView)view.findViewById(R.id.date1);
 				  final TextView username=(TextView)view.findViewById(R.id.publisher1);
 				  final TextView clandcf=(TextView)view.findViewById(R.id.clandcf);
 				  final EditText conflict=(EditText)view.findViewById(R.id.conflict);
 				  final Button delete=(Button)view.findViewById(R.id.delete1);
 				  if(p==0) delete.setVisibility(View.INVISIBLE);
       			  delete.setOnClickListener(new OnClickListener(){
       				  public void onClick(View v){
       					  String mes;
       	     			 AlertDialog.Builder builder=new Builder(NoteListActivity.this);
       	    			 builder.setTitle("��ʾ")
       	    			 .setMessage("ȷ��ɾ���ñʼ���")
       	    			 .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
       	    				@Override
       	    				public void onClick(DialogInterface dialog, int which) {
       	    					String noteid=note_id.getText().toString();
       	    					String topicid=topic_id.getText().toString();
       	    					String url=processURL_deleteNote+"topicid="+topicid+"&noteid="+noteid;
       	    					remoteDelete(url);
       	    					dialog.dismiss();
       	    				}
       	    			}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
       						
       						@Override
       						public void onClick(DialogInterface dialog, int which) {
       							// TODO Auto-generated method stub
       							dialog.dismiss();
       						}
       					}).create().show();
       				  }
       			  });
       		      LinearLayout view1=(LinearLayout)view.findViewById(R.id.linearlayout);
       			  view1.setOnClickListener(new OnClickListener(){
       				  public void onClick(View v){
               		         Intent noteitem = new Intent(NoteListActivity.this,NoteItemActivity.class);
               		         Bundle noteitembundle = new Bundle();//����ֻ�ܴ���ȥ���getview�����ݣ���ͬʱ��Ҫˢ�����ݣ�ֻ���ٷ���һ�����ݿ�
            		         String  topicidstr=topic_id.getText().toString();
               		         String  noteidstr=note_id.getText().toString();
               		         String  titlestr=title.getText().toString();
               		         String  usernamestr=username.getText().toString();
               		         String  notestr=note.getText().toString();
               		         String  conclusionstr=conclusion.getText().toString();
               		         String  conflictstr=clandcf.getText().toString();
               		         String  memberstr=member.getText().toString();
               		         String  datestr=date.getText().toString();
               		         String  sitestr=site.getText().toString();
               		         noteitembundle.putString("topicid", topicidstr);
               		         noteitembundle.putString("noteid", noteidstr);
               		         noteitembundle.putString("userid", myid);
               		         noteitembundle.putString("conflict", conflictstr);
               		         noteitembundle.putString("username", usernamestr);
               		         noteitembundle.putString("conclusions", conclusionstr);
               		         noteitembundle.putString("date", datestr);
               		         noteitembundle.putString("site", sitestr);
               		         noteitembundle.putString("note", notestr);
               		         noteitembundle.putString("member", memberstr);
               		         noteitembundle.putString("title", titlestr);
               		         noteitem.putExtras(noteitembundle);
               		         startActivity(noteitem);
       				  }
       			  });
       			  Button addconflict=(Button)view.findViewById(R.id.addconflict);
       			  addconflict.setOnClickListener(new OnClickListener(){
       				  public void onClick(View v){
       					String conflictstr=conflict.getText().toString();
       					String topicid=topic_id.getText().toString();
       					String noteid=note_id.getText().toString();
       					String clandcfstr=clandcf.getText().toString();
       					String datestr=getDate();
       					//Toast.makeText(NoteListActivity.this, noteid, Toast.LENGTH_LONG).show();
       					boolean f=addRemoteConflict(clandcfstr,conflictstr,topicid,noteid,datestr);
       					if(f){
       			    	  /*String URL=processURL+"topicid="+topicid;
       			    	  remote(URL);*/
       			    	  Toast.makeText(NoteListActivity.this, "��ӳɹ���", Toast.LENGTH_LONG).show();
       					}
       					else{
       						Toast.makeText(NoteListActivity.this, "ͨ�ų���", Toast.LENGTH_LONG).show();
       					}
       				  }
       			  });
       			  return view;
       		  }
       	  };
       	  ListView listview = getListView();
       	  listview.setAdapter(adapter);//���������
       	 /* listview.setOnItemClickListener(new OnItemClickListener(){
       		  public void onItemClick(AdapterView<?> parent,View view,int position,long id){
       		         HashMap<String, String>  map = (HashMap<String, String>) ((ListView) parent).
       				 getItemAtPosition(position); 
       		         String  topicidstr=map.get("topicid");
       		         String  noteidstr=map.get("id");
       		         String  titlestr=map.get("title");
       		         String  usernamestr=map.get("username");
       		         String  notestr=map.get("note");
       		         String  conclusionstr=map.get("conclusion");
       		         String  conflictstr=map.get("conclusion1");
       		         String  memberstr=map.get("member");
       		         String  datestr=map.get("date");
       		         String  sitestr=map.get("site");
       		         Intent noteitem = new Intent(NoteListActivity.this,NoteItemActivity.class);
       		         Bundle noteitembundle = new Bundle();
       		         noteitembundle.putString("topicid", topicidstr);
       		         noteitembundle.putString("noteid", noteidstr);
       		         noteitembundle.putString("userid", myid);
       		         noteitembundle.putString("conflict", conflictstr);
       		         noteitembundle.putString("username", usernamestr);
       		         noteitembundle.putString("conclusions", conclusionstr);
       		         noteitembundle.putString("date", datestr);
       		         noteitembundle.putString("site", sitestr);
       		         noteitembundle.putString("note", notestr);
       		         noteitembundle.putString("member", memberstr);
       		         noteitembundle.putString("title", titlestr);
       		         noteitem.putExtras(noteitembundle);
       		         startActivity(noteitem);
       		  }
       	  });*/
      }
  	public void remoteDelete(String url){
    	try {
	    	HttpClient httpclient = new DefaultHttpClient();
	    	HttpPost request=new HttpPost(url);
	    	if(request==null) Log.d("mylog","request==null");
	    	request.addHeader("Accept","text/json");
			HttpResponse response =httpclient.execute(request);
			if(response==null) Log.d("mylog","response==null");
			HttpEntity entity=response.getEntity();
			String json =EntityUtils.toString(entity,"UTF-8");
			if(json!=null){
				JSONObject jsonObject=new JSONObject(json);
				result=jsonObject.get("message").toString().trim();
			}
		  if("true".equals(result)){
			  Toast.makeText(NoteListActivity.this, "ɾ���ɹ���", Toast.LENGTH_LONG).show();
	    	  String URL=processURL+"topicid="+topicid;
	    	  remote(URL);
		   }else{
			   Toast.makeText(NoteListActivity.this, "ͨ�ų���", Toast.LENGTH_LONG).show();
		   }
    	 } catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
  	private String getDate(){
		java.util.Date date = new java.util.Date();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String res = df.format(date);
		return res;
	}
  	  private List<Map<String, Object>> getData(Topic topic,ArrayList<Note> list1) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String str[]=new String[100];
        	Map<String, Object> map = new HashMap<String, Object>();
        	map.put("title", topic.getTitle());        	
        	map.put("note", topic.getNote());
        	map.put("date", topic.getDate());
        	map.put("topicid", topic.getId());
        	map.put("id", "-1");
        	map.put("member", topic.getMember());
        	map.put("userid", topic.getUserid());
        	map.put("conclusion1", topic.getConclusion());
        	str=topic.getConclusion().split(";");
        	map.put("conclusion", str[0]);
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
            	map1.put("conclusion1", list1.get(i).getConclusion());
            	String [] str1=list1.get(i).getConclusion().split(";");
            	map1.put("conclusion", str1[0]);
            	map1.put("site", list1.get(i).getSite());
            	name=remote.getRemoteUserName(list1.get(i).getUserid());
            	map1.put("username", name);
            	list.add(map1);
        	}
        	Log.d("mylog","list size = "+list.size());
		return list;
	}
      private boolean addRemoteConflict(String conclusion,String conflict,String topicid,String noteid,String date){//��Զ�˷����¼�ì�ܵ�noteid,topicid,ì�ܣ��Լ�����
      	try{
    	    HttpClient httpclient = new DefaultHttpClient();
    	    String myname=remote.getRemoteUserName(myid);
    	    conclusion=conclusion+";"+conflict+"\\("+myname+"-"+date+"\\)";
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
      private void remote(String url){//��õ�ǰ������Ϣ
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
  		            String NOTEURL=processURL_findNoteList+"&topicid="+id+"&f="+"1";
  		            remoteNote(topic,NOTEURL);
  		    	}
  		} catch (IOException e) {
  			e.printStackTrace();
  		} catch(JSONException e){
  			e.printStackTrace();
  		}
      }
      private void remoteNote(Topic topic,String url){//��ø���������бʼǲ�����show
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
