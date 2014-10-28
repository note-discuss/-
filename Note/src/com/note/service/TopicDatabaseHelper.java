package com.note.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TopicDatabaseHelper extends SQLiteOpenHelper {
	static String name="topic.db";
	static int dbVersion=1;
	public TopicDatabaseHelper(Context context) {
		super(context, name, null, dbVersion);
	}
	//只在创建的时候用一次
	public void onCreate(SQLiteDatabase db) {
		String sql="create table topic(id integer primary key autoincrement,"
		+ "userid varchar(20),title varchar(30),note varchar(300),conclusion varchar(200))";
		db.execSQL(sql);
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
    public Cursor queryNote(String tbl_name){
    	SQLiteDatabase db= getReadableDatabase();
    	Cursor c= db.query(tbl_name, new String[]{"id as _id","title","note","conclusion"},
    			null, null, null, null, null, null);
    	return c;
    }
}