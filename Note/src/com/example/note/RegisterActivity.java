package com.example.note;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;




import com.note.domain.User;
import com.note.service.UserService;

public class RegisterActivity extends Activity {
    private static  String processURL="http://172.17.133.231:8080/ServerProject/jregister.action?";
	private final String processURL_constant  = "http://172.17.133.231:8080/ServerProject/jregister.action?";
	EditText username;
	EditText password;
	EditText id;
	RadioGroup sex;	
	RadioGroup role;	
	Button register;
	String result=null;
	String op=null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		findViews();
		 StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()   
         .detectDiskReads()   
         .detectDiskWrites()   
         .detectNetwork()   // or .detectAll() for all detectable problems   
         .penaltyLog()   
         .build());   
		//设置虚拟机的策略
		  StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()   
		         .detectLeakedSqlLiteObjects()   
		         //.detectLeakedClosableObjects()   
		         .penaltyLog()   
		         .penaltyDeath()   
		         .build());
		register.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String name=username.getText().toString().trim();
				String pass=password.getText().toString().trim();
				String idstr=id.getText().toString().trim();
				String sexstr=((RadioButton)RegisterActivity.this.findViewById(sex.getCheckedRadioButtonId())).getText().toString();
				String rolestr=((RadioButton)RegisterActivity.this.findViewById(role.getCheckedRadioButtonId())).getText().toString();
				//public int getCheckedRadioButtonId ()   返回该单选按钮组中所选择的单选按钮的标识ID，如果没有勾选则返回-1
				Log.i("TAG",name+"_"+pass+"_"+idstr+"_"+sexstr);
				if(idstr.length()==0){
				Toast.makeText(RegisterActivity.this, "ID不能为空！", Toast.LENGTH_LONG).show();
				}
					registerRemoteService(name,pass,idstr,sexstr,rolestr);
				/*else{
				UserService uService=new UserService(RegisterActivity.this);
				boolean f=uService.haveid(idstr);
				if(f==true){
					Toast.makeText(RegisterActivity.this,"ID已存在！",Toast.LENGTH_LONG).show();
				}else{
				    User user=new User();
				    user.setName(name);
				    user.setPassword(pass);
				    user.setId(idstr);
				    user.setSex(sexstr);
				    user.setRole(rolestr);
				    uService.register(user);
				    Toast.makeText(RegisterActivity.this, "注册成功!", Toast.LENGTH_LONG).show();
				  }
				}*/
			}
		});
	}
	private void findViews() {
		username=(EditText) findViewById(R.id.usernameRegister);
		password=(EditText) findViewById(R.id.passwordRegister);
		id=(EditText) findViewById(R.id.idRegister);
		sex=(RadioGroup) findViewById(R.id.sexRegister);
		role=(RadioGroup) findViewById(R.id.roleRegister);
		register=(Button) findViewById(R.id.Register);
	}
	public void registerRemoteService(String username,String password,
			final String id,String sex,String role){
    	try {
   		 
	    	//创建一个HttpClient对象
	    	HttpClient httpclient = new DefaultHttpClient();
	    	//远程登录URL
	    	//下面这句是原有的
	    	//processURL=processURL+"userName="+userName+"&password="+password;
	    	processURL= processURL_constant+"username="+username+"&password="+password+
	    			"&id="+id+"&sex="+sex+"&role="+role;
	    	Log.d("mylog", processURL);
	        //创建HttpGet对象
	    	HttpPost request=new HttpPost(processURL);
	    	Log.d("mylog","request");
	    	if(request==null) Log.d("mylog","request==null");
	    	//请求信息类型MIME每种响应类型的输出（普通文本、html 和 XML，json）。允许的响应类型应当匹配资源类中生成的 MIME 类型
	    	//资源类生成的 MIME 类型应当匹配一种可接受的 MIME 类型。如果生成的 MIME 类型和可接受的 MIME 类型不 匹配，那么将
	    	//生成 com.sun.jersey.api.client.UniformInterfaceException。例如，将可接受的 MIME 类型设置为 text/xml，而将
	    	//生成的 MIME 类型设置为 application/xml。将生成 UniformInterfaceException。
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
			//JSON的解析过程
			if(json!=null){
				Log.d("mylog",json);
				JSONObject jsonObject=new JSONObject(json);
				 Log.d("mylog","new json");
				result=jsonObject.get("message").toString().trim();
				 Log.d("mylog","result="+result);
			}
		   if(result==null){ 
			   Log.d("mylog","result=null");
			   json="登录失败请重新登录";
		   }
			//创建提示框提醒是否登录成功
		   Log.d("mylog","AlertDialog");
			 AlertDialog.Builder builder=new Builder(RegisterActivity.this);
			 builder.setTitle("提示")
			 .setMessage(result)
			 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if("注册成功！点击确定登录！".equals(result)){
						Intent classnote=new Intent(RegisterActivity.this,ClassNoteActivity.class);//启动register活动
						Bundle bundle= new Bundle();
						bundle.putString("id", id);
						classnote.putExtras(bundle);
						startActivity(classnote);
					}
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
}
