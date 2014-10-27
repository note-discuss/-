package com.note.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import com.note.domain.Topic;
import com.note.domain.User;

public class TopicService {
	private TopicDatabaseHelper dbHelper;
	public TopicService(Context context){
		dbHelper=new TopicDatabaseHelper(context);
    }
	public boolean insert(Topic topic){
		SQLiteDatabase sdb=dbHelper.getReadableDatabase();
		String sql="insert into topic(userid,title,note,conclusion) values(?,?,?,?)";
		Object obj[]={topic.getUserId(),topic.getTitle(),topic.getNote(),topic.getConclusion()};
		sdb.execSQL(sql, obj);	
		return true;
	}
	
}