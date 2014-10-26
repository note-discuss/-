package com.example.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.note.R;
import com.note.service.UserService;


public class LoginActivity extends Activity {
	EditText id;
	EditText password;
	Button login,register;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	//savedInstanceState用于在pause时保存当前activity的状态
		setContentView(R.layout.main);  //这句话的意思是这个activity对应的布局是res/layout目录下的main.xml文件
		findViews();
	}
	private void findViews() {
		id=(EditText) findViewById(R.id.id);  //生成输入文本框
		password=(EditText) findViewById(R.id.password);
		login=(Button) findViewById(R.id.login);           //生成一个按钮，与布局里的login对应
		register=(Button) findViewById(R.id.register);
		login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {         //通过view可以获取点击的id，可以知道点击了哪个按钮
				String idstr=id.getText().toString();
				String passstr=password.getText().toString();
				Log.i("TAG",idstr+"_"+passstr);               //调试的时候输出name 和password
				UserService uService=new UserService(LoginActivity.this);  //注意这里传入LoginActivity.this，获得当前activity的上下文
				int flag=uService.login(idstr, passstr);
				if(flag==1){
					Log.i("TAG","登录成功!");               //提示登录成功
					Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_LONG).show(); //以默认效果在屏幕显示登陆成功
					Intent classnote=new Intent(LoginActivity.this,ClassNoteActivity.class);//启动register活动
					Bundle bundle= new Bundle();
					bundle.putString("id", idstr);
					classnote.putExtra("bundle", bundle);
					startActivity(classnote);
				}else{
					if(flag==0){
						Log.i("TAG","ID不存在！");
						Toast.makeText(LoginActivity.this, "ID不存在！", Toast.LENGTH_LONG).show();
					}else{
					Log.i("TAG","用户名或密码错误！");
					Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_LONG).show();
					}
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

}