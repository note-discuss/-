package com.example.note;

import android.app.Activity;
import android.os.Bundle;
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
				}else{
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
				}
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

}
