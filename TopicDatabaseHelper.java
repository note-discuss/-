package com.note.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TopicDatabaseHelper extends SQLiteOpenHelper {
	static String name="topic.db";
	static int dbVersion=1;
	public TopicDatabaseHelper(Context context) {
		super(context, name, null, dbVersion);
	}
	//ֻ�ڴ�����ʱ����һ��
	public void onCreate(SQLiteDatabase db) {
		String sql="create table topic(id integer primary key autoincrement,username varchar(20),title varchar(30),question varchar(300))";
		db.execSQL(sql);
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}