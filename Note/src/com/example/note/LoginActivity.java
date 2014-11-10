package com.example.note;

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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note.R;
import com.note.service.UserService;
import com.note.service.remoteURL;


public class LoginActivity extends Activity {
	static remoteURL remote = new remoteURL();
    private static  String processURL=remote.remoteURL+"jlogin.action?";
	private final String processURL_constant  = remote.remoteURL+"jlogin.action?";
	String result=null;
	EditText id;
	EditText password;
	Button login,register;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		findViews();
		 StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()   
         .detectDiskReads()   
         .detectDiskWrites()   
         .detectNetwork()   
         .penaltyLog()   
         .build());   
		  StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()   
		         .detectLeakedSqlLiteObjects()    
		         .penaltyLog()   
		         .penaltyDeath()   
		         .build());
		super.onCreate(savedInstanceState);	//savedInstanceState用于在pause时保存当前activity的状态
	}
	private void findViews() {
		id=(EditText) findViewById(R.id.id);  //生成输入文本框
		password=(EditText) findViewById(R.id.password);
		login=(Button) findViewById(R.id.login);           //生成一个按钮，与布局里的login对应
		register=(Button) findViewById(R.id.register);
		login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {         //通过view可以获取点击的id，可以知道点击了哪个按钮
				String idstr=id.getText().toString().trim();
				String passstr=password.getText().toString().trim();
				Log.i("TAG",idstr+"_"+passstr);               //调试的时候输出name 和password
				if(idstr.length()==0){
					Toast.makeText(LoginActivity.this, "请输入ID！", Toast.LENGTH_LONG).show();
				}else{
				/*UserService uService=new UserService(LoginActivity.this);  //注意这里传入LoginActivity.this，获得当前activity的上下文
				int flag=uService.login(idstr, passstr);
				if(flag==1){
					Log.i("TAG","登录成功!");               //提示登录成功
					Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_LONG).show(); //以默认效果在屏幕显示登陆成功
					Intent classnote=new Intent(LoginActivity.this,ClassNoteActivity.class);//启动register活动
					Bundle bundle= new Bundle();
					bundle.putString("id", idstr);
					classnote.putExtras(bundle);
					startActivity(classnote);
				}else{
					if(flag==0){
						Log.i("TAG","ID不存在！");
						Toast.makeText(LoginActivity.this, "ID不存在！", Toast.LENGTH_LONG).show();
					}else{
					Log.i("TAG","用户名或密码错误！");
					Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_LONG).show();
					}
				}*/
				loginRemoteService(idstr,passstr);
				}
			}
		});
		register.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);//启动register活动
				startActivity(intent);
			}
		});
	}
	public void loginRemoteService(final String id,String password){
    	try {
	    	HttpClient httpclient = new DefaultHttpClient();
	    	processURL= processURL_constant+"id="+id+"&password="+password;
	    	Log.d("mylog", processURL);
	        //创建HttpGet对象
	    	HttpPost request=new HttpPost(processURL);
	    	Log.d("mylog","request");
	    	if(request==null) Log.d("mylog","request==null");
	    	request.addHeader("Accept","text/json");
	        //获取响应的结果
	    	Log.d("mylog","addHeader");
			HttpResponse response =httpclient.execute(request);
			Log.d("mylog","response");
			if(response==null) Log.d("mylog","response==null");
			//获取HttpEntity
			HttpEntity entity=response.getEntity();
			//获取响应的结果信息
			String json =EntityUtils.toString(entity,"UTF-8");
			if(json!=null){
				Log.d("mylog",json);
				JSONObject jsonObject=new JSONObject(json);
				 Log.d("mylog","new json");
				result=jsonObject.get("message").toString().trim();
				 Log.d("mylog","result="+result);
			}
		   if(result==null){ 
			   Log.d("mylog","result=null");
			   json="登录失败,请重新登录!";
			   Toast.makeText(LoginActivity.this, json, Toast.LENGTH_LONG).show();
		   }else if("登录成功！".equals(result)){
			   Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_LONG).show();
				Intent classnote=new Intent(LoginActivity.this,ClassNoteActivity.class);//启动register活动
				Bundle bundle= new Bundle();
				bundle.putString("id", id);
				classnote.putExtras(bundle);
				startActivity(classnote);
		   }
			//创建提示框提醒是否登录成功
		  /* Log.d("mylog","AlertDialog");
			 AlertDialog.Builder builder=new Builder(LoginActivity.this);
			 builder.setTitle("提示")
			 .setMessage(result)
			 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if("登录成功！".equals(result)){
						Intent classnote=new Intent(LoginActivity.this,ClassNoteActivity.class);//启动register活动
						Bundle bundle= new Bundle();
						bundle.putString("id", id);
						classnote.putExtras(bundle);
						startActivity(classnote);
					}
					dialog.dismiss();
				}
			}).create().show();*/
		 
    	 } catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}