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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note.R;
import com.note.service.DataHelper;
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
	protected CheckBox remember;
	protected DataHelper helper;
	protected final String db_remember = "remember";
	protected final String db_name = "name";
	protected final String db_password = "password";
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
		super.onCreate(savedInstanceState);	//savedInstanceState������pauseʱ���浱ǰactivity��״̬
	}
	private void saveData() {
		helper.putBoolean(db_remember, remember.isChecked());
		if (remember.isChecked()) {
			helper.putString(db_name, id.getText().toString());
			helper.putString(db_password, password.getText().toString());
		}
	}
	private void findViews() {
		helper = DataHelper.getInstance(this);
		remember = (CheckBox) findViewById(R.id.remember);
		id=(EditText) findViewById(R.id.id);  //���������ı���
		password=(EditText) findViewById(R.id.password);
		login=(Button) findViewById(R.id.login);           //����һ����ť���벼�����login��Ӧ
		register=(Button) findViewById(R.id.register);
		remember.setChecked(helper.getBoolean(db_remember, true));
		String NAME = helper.getString(db_name, null);
		String PWD = helper.getString(db_password, null);
		if (NAME != null && PWD != null) {
			id.setText(NAME);
			password.setText(PWD);
		}
		login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {         //ͨ��view���Ի�ȡ�����id������֪��������ĸ���ť
				String idstr=id.getText().toString().trim();
				String passstr=password.getText().toString().trim();
				Log.i("TAG",idstr+"_"+passstr);               //���Ե�ʱ�����name ��password
				if(idstr.length()==0){
					Toast.makeText(LoginActivity.this, "������ID��", Toast.LENGTH_LONG).show();
				}else{
				/*UserService uService=new UserService(LoginActivity.this);  //ע�����ﴫ��LoginActivity.this����õ�ǰactivity��������
				int flag=uService.login(idstr, passstr);
				if(flag==1){
					Log.i("TAG","��¼�ɹ�!");               //��ʾ��¼�ɹ�
					Toast.makeText(LoginActivity.this, "��¼�ɹ���", Toast.LENGTH_LONG).show(); //��Ĭ��Ч������Ļ��ʾ��½�ɹ�
					Intent classnote=new Intent(LoginActivity.this,ClassNoteActivity.class);//����register�
					Bundle bundle= new Bundle();
					bundle.putString("id", idstr);
					classnote.putExtras(bundle);
					startActivity(classnote);
				}else{
					if(flag==0){
						Log.i("TAG","ID�����ڣ�");
						Toast.makeText(LoginActivity.this, "ID�����ڣ�", Toast.LENGTH_LONG).show();
					}else{
					Log.i("TAG","�û������������");
					Toast.makeText(LoginActivity.this, "�û������������", Toast.LENGTH_LONG).show();
					}
				}*/
				loginRemoteService(idstr,passstr);
				}
			}
		});
		register.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);//����register�
				startActivity(intent);
			}
		});
	}
	public void loginRemoteService(String id,String password){
    	try {
	    	HttpClient httpclient = new DefaultHttpClient();
	    	id=java.net.URLEncoder.encode(id,"utf-8");
	    	password=java.net.URLEncoder.encode(password,"utf-8");
	    	processURL= processURL_constant+"id="+id+"&password="+password;
	    	Log.d("mylog", processURL);
	        //����HttpGet����
	    	HttpPost request=new HttpPost(processURL);
	    	Log.d("mylog","request");
	    	if(request==null) Log.d("mylog","request==null");
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
			if(json!=null){
				Log.d("mylog",json);
				JSONObject jsonObject=new JSONObject(json);
				 Log.d("mylog","new json");
				result=jsonObject.get("message").toString().trim();
				 Log.d("mylog","result="+result);
			}
		  if("��¼�ɹ���".equals(result)){
			   saveData();
			   Toast.makeText(LoginActivity.this, "��½�ɹ���", Toast.LENGTH_LONG).show();
				Intent classnote=new Intent(LoginActivity.this,ClassNoteActivity.class);//����register�
				Bundle bundle= new Bundle();
				bundle.putString("id", id);
				classnote.putExtras(bundle);
				startActivity(classnote);
		   }else{
			   Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG).show();
		   }
			//������ʾ�������Ƿ��¼�ɹ�
		  /* Log.d("mylog","AlertDialog");
			 AlertDialog.Builder builder=new Builder(LoginActivity.this);
			 builder.setTitle("��ʾ")
			 .setMessage(result)
			 .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if("��¼�ɹ���".equals(result)){
						Intent classnote=new Intent(LoginActivity.this,ClassNoteActivity.class);//����register�
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