package com.note.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {
	static String name="user.db";
	static int dbVersion=1;
	public UserDatabaseHelper(Context context) {
		super(context, name, null, dbVersion);
	}
	//只在创建的时候用一次
	public void onCreate(SQLiteDatabase db) {
		String sql="create table user(id varchar(20) primary key,name varchar(20),password varchar(20), role varchar(10), sex varchar(2))";
		db.execSQL(sql);
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
    public Cursor queryUser(String tbl_name,String idstr){
    	SQLiteDatabase db= getReadableDatabase();
    	String sql= "id=?";
    	Cursor c= db.query(tbl_name, new String[]{"id as _id","name","sex","role"},
    			sql, new String[]{idstr}, null, null, null, null);
    	return c;
    }
}
