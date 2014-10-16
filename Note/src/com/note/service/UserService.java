package com.note.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.note.domain.User;

public class UserService {
	private DatabaseHelper dbHelper;
	public UserService(Context context){
		dbHelper=new DatabaseHelper(context);
	}
	
	//µÇÂ¼ÓÃ
	public boolean login(String id,String password){
		SQLiteDatabase sdb=dbHelper.getReadableDatabase();
		String sql="select * from user where id=? and password=?";
		Cursor cursor=sdb.rawQuery(sql, new String[]{id,password});		
		if(cursor.moveToFirst()==true){
			cursor.close();
			return true;
		}
		return false;
	}
	//×¢²áÓÃ
	public boolean register(User user){
		SQLiteDatabase sdb=dbHelper.getReadableDatabase();
		String sql="insert into user(id,name,password,role,sex) values(?,?,?,?,?)";
		Object obj[]={user.getId(),user.getName(),user.getPassword(),user.getRole(), user.getSex()};
		sdb.execSQL(sql, obj);	
		return true;
	}
}
