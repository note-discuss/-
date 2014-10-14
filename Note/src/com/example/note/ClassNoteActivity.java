package com.example.note;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import com.example.note.R;

public class ClassNoteActivity extends Activity{
	  EditText note;
      public void onCreate(Bundle savedInstanceState){
    	  super.onCreate(savedInstanceState);
  		  setContentView(R.layout.note);
  		  findViews();  
      }
      public void findViews(){
    	  
      }
}
