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
import com.note.service.remoteURL;

public class RegisterActivity extends Activity {
	static remoteURL remote = new remoteURL();
    private static  String processURL=remote.remoteURL+"jregister.action?";
	private final String processURL_constant  = remote.remoteURL+"jregister.action?";
	String result=null;
	EditText username;
	EditText password;
	EditText id;
	RadioGroup sex;	
	RadioGroup role;	
	Button register;
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
		//����������Ĳ���
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
				//public int getCheckedRadioButtonId ()   ���ظõ�ѡ��ť������ѡ��ĵ�ѡ��ť�ı�ʶID�����û�й�ѡ�򷵻�-1
				Log.i("TAG",name+"_"+pass+"_"+idstr+"_"+sexstr);
				if(idstr.length()==0){
				Toast.makeText(RegisterActivity.this, "ID����Ϊ�գ�", Toast.LENGTH_LONG).show();
				}
					registerRemoteService(name,pass,idstr,sexstr,rolestr);
				/*else{
				UserService uService=new UserService(RegisterActivity.this);
				boolean f=uService.haveid(idstr);
				if(f==true){
					Toast.makeText(RegisterActivity.this,"ID�Ѵ��ڣ�",Toast.LENGTH_LONG).show();
				}else{
				    User user=new User();
				    user.setName(name);
				    user.setPassword(pass);
				    user.setId(idstr);
				    user.setSex(sexstr);
				    user.setRole(rolestr);
				    uService.register(user);
				    Toast.makeText(RegisterActivity.this, "ע��ɹ�!", Toast.LENGTH_LONG).show();
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
   		 
	    	//����һ��HttpClient����
	    	HttpClient httpclient = new DefaultHttpClient();
	    	//Զ�̵�¼URL
	    	//���������ԭ�е�
	    	username=java.net.URLEncoder.encode(username,"utf-8");
	    	sex=java.net.URLEncoder.encode(sex,"utf-8");
	    	role=java.net.URLEncoder.encode(role,"utf-8");
	    	//processURL=processURL+"userName="+userName+"&password="+password;
	    	processURL= processURL_constant+"username="+username+"&password="+password+
	    			"&id="+id+"&sex="+sex+"&role="+role;
	    	Log.d("mylog", processURL);
	        //����HttpGet����
	    	HttpPost request=new HttpPost(processURL);
	    	request.setHeader("Content-Type", "charset=utf-8");
	    	Log.d("mylog","request");
	    	if(request==null) Log.d("mylog","request==null");
	    	//������Ϣ����MIMEÿ����Ӧ���͵��������ͨ�ı���html �� XML��json�����������Ӧ����Ӧ��ƥ����Դ�������ɵ� MIME ����
	    	//��Դ�����ɵ� MIME ����Ӧ��ƥ��һ�ֿɽ��ܵ� MIME ���͡�������ɵ� MIME ���ͺͿɽ��ܵ� MIME ���Ͳ� ƥ�䣬��ô��
	    	//���� com.sun.jersey.api.client.UniformInterfaceException�����磬���ɽ��ܵ� MIME ��������Ϊ text/xml������
	    	//���ɵ� MIME ��������Ϊ application/xml�������� UniformInterfaceException��
	    	request.addHeader("Accept","text/json");
	        //��ȡ��Ӧ�Ľ��
	    	Log.d("mylog","addHeader");
			HttpResponse response =httpclient.execute(request);
			Log.d("mylog","response");
			if(response==null) Log.d("mylog","response==null");
			//��ȡHttpEntity
			HttpEntity entity=response.getEntity();
			//��ȡ��Ӧ�Ľ����Ϣ
			String json =EntityUtils.toString(entity,"UTF-8");
			//JSON�Ľ�������
			if(json!=null){
				JSONObject jsonObject=new JSONObject(json);
				result=jsonObject.get("message").toString().trim();
			}
		   if(result==null){ 
			   Log.d("mylog","result=null");
			   json="��¼ʧ�������µ�¼";
		   }
			//������ʾ�������Ƿ��¼�ɹ�
		   Log.d("mylog","AlertDialog");
			 AlertDialog.Builder builder=new Builder(RegisterActivity.this);
			 builder.setTitle("��ʾ")
			 .setMessage(result)
			 .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if("ע��ɹ������ȷ����¼��".equals(result)){
						Intent classnote=new Intent(RegisterActivity.this,ClassNoteActivity.class);//����register�
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
