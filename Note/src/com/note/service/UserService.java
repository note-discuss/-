package com.note.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.note.domain.User;

public class UserService {
	private UserDatabaseHelper dbHelper;
	public UserService(Context context){
		dbHelper=new UserDatabaseHelper(context);
	}
	
	//µÇÂ¼ÓÃ
	public int login(String id,String password){
		SQLiteDatabase sdb=dbHelper.getReadableDatabase();
		String sql0="select * from user where id=?";
		Cursor cursor0=sdb.rawQuery(sql0, new String[]{id});
		if(cursor0.moveToFirst()==true){
			cursor0.close();
		}else{
			return 0;
		}
		String sql="select * from user where id=? and password=?";
		Cursor cursor=sdb.rawQuery(sql, new String[]{id,password});		
		if(cursor.moveToFirst()==true){
			cursor.close();
			return 1;
		}
		return -1;
	}
	public boolean haveid(String id){
		SQLiteDatabase sdb=dbHelper.getReadableDatabase();
		String sql0="select * from user where id=?";
		Cursor cursor0=sdb.rawQuery(sql0, new String[]{id});
		if(cursor0.moveToFirst()==true){
			cursor0.close();
			return true;
		}else{
			return false;
		}
	}
	//×¢²áÓÃ
	public User query(String idstr){
		SQLiteDatabase sdb=dbHelper.getReadableDatabase();
		String sql= "select * from user where id=?";
		Cursor cursor=sdb.rawQuery(sql,new String[]{idstr});
		String id = cursor.getString( cursor.getColumnIndex("id") ); 
		String name = cursor.getString( cursor.getColumnIndex("name") ); 
		String password= cursor.getString( cursor.getColumnIndex("password") ); 
		String role = cursor.getString( cursor.getColumnIndex("role") ); 
		String sex = cursor.getString( cursor.getColumnIndex("sex") ); 
		User user = new User(id,name,password,role,sex);
		return user;
	}
	public Cursor query1(String idstr){
		SQLiteDatabase sdb=dbHelper.getReadableDatabase();
		String sql= "select * from user where id=?";
		Cursor cursor=sdb.rawQuery(sql,new String[]{idstr});
		return cursor;
	}
	public boolean register(User user){
		SQLiteDatabase sdb=dbHelper.getReadableDatabase();
		String sql="insert into user(id,name,password,role,sex) values(?,?,?,?,?)";
		Object obj[]={user.getId(),user.getUsername(),user.getPassword(),user.getRole(), user.getSex()};
		sdb.execSQL(sql, obj);	
		return true;
	}
}
