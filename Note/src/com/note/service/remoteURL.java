package com.note.service;

import java.io.IOException;
import java.util.ArrayList;

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
import com.note.domain.User;

import android.util.Log;

public class remoteURL {
      final public String remoteURL = "http://192.168.253.1:8080/ServerProject/";
      String result;
      String res[];
      public String[] getRemoteString(String processURL_findstring,String prefix){
      	try{
      	    HttpClient httpclient = new DefaultHttpClient();
      	    prefix=java.net.URLEncoder.encode(prefix,"utf-8");
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
  		    	res[j]=res[j]+")";
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
      public ArrayList<User> getRemoteUser(String userid){
    	   ArrayList<User> list=null;
        	try{
        	    HttpClient httpclient = new DefaultHttpClient();
        	    userid=java.net.URLEncoder.encode(userid,"utf-8");
             	final String URL= remoteURL+"getUser.action?"+"userid="+userid;
                HttpPost request=new HttpPost(URL);
        	    request.addHeader("Accept","text/json");
    		    HttpResponse response =httpclient.execute(request);
    		    HttpEntity entity=response.getEntity();
    		    String json =EntityUtils.toString(entity,"UTF-8");
    		    if(json!=null){
    				JSONObject jsonObject=new JSONObject(json);
    				result=jsonObject.get("UserList").toString().trim();
    				if(!result.equals("null")){
    					list = new ArrayList<User>();
    		    	    JSONArray jsonarray = new JSONArray(result);
    		    	    int len = jsonarray.length();
    		    	    for(int i=0;i<len;++i){
    		    	    	JSONObject obj = jsonarray.getJSONObject(i);
    		    	    	String id=obj.getString("id");
    		    	    	String username=obj.getString("username");
    		    	    	String password=obj.getString("password");
    		    	    	String role=obj.getString("role");
    		    	    	String sex=obj.getString("sex");
    		    	    	User user=new User(id,username,password,role,sex);
    		    	    	list.add(user);
    		    	    }
    				}
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
        	return list;
        }
      public String getRemoteUserName(String userid){
    	  ArrayList<User> list=getRemoteUser(userid);
    	  String res=null;
    	  if(list==null) return res;
    	  return list.get(0).getUsername();
      }
}
