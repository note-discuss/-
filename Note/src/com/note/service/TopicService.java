package com.note.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.note.domain.Topic;
import com.note.domain.User;

public class TopicService {
	private TopicDatabaseHelper dbHelper;
	public TopicService(Context context){
		dbHelper=new TopicDatabaseHelper(context);
    }
	public boolean insert(Topic topic){
		SQLiteDatabase sdb=dbHelper.getReadableDatabase();
		String sql="insert into topic(userid,title,question) values(?,?,?)";
		Object obj[]={topic.getUserId(),topic.getTitle(),topic.getQuestion()};
		sdb.execSQL(sql, obj);	
		return true;
	}
}