package com.example.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class UserInfoActivity extends Activity{
	 
     public void onCreate(Bundle savedInstanceState){
    	 super.onCreate(savedInstanceState);
    	 setContentView(R.layout.userinfo);
    	 findviews();
     }
     private void findviews(){
    	 Intent intent = this.getIntent();
    	 Bundle bundle = intent.getExtras();
    	 String id = bundle.getString("id");
     }
}
