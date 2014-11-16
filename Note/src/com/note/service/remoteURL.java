package com.note.service;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class remoteURL {
      final public String remoteURL = "http://192.168.1.113:8080/ServerProject/";
      String result;
      String res[];
      public String[] getRemoteString(String processURL_findstring,String prefix){
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
}
