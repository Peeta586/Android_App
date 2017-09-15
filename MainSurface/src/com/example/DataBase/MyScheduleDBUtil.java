package com.example.DataBase;

import com.example.Alarm.AlarmforSch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyScheduleDBUtil {
	private Context context;
	private MyTodoScheduleDB helper;
	public MyScheduleDBUtil(Context context) {
		this.context =context;
		helper = new MyTodoScheduleDB(context);
	}
	public void Insert(ContentValues values,int remind,int repeat_type){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.insert("Schedule", null, values);
		/**
		 * 设置提醒
		 */
		Cursor cursor=db.query("Schedule", new String[]{"id"}, null, null, null, null, "id desc");
		int id =0;
		//求出当前最大的id，也就是刚存入的id
		if(cursor.moveToNext()){
			id =cursor.getInt(cursor.getColumnIndex("id"));
		}
		if(cursor !=null)
			cursor.close();
		//得到id，根据id设置闹铃。
		if(remind !=-1){//如果有提醒
			if(repeat_type !=0){//如果有重复
				new AlarmforSch(context, id).setRepeatAlarm();
			}else{//如果不重复
				new AlarmforSch(context, id).setAlarm();
			}
		}else{
			new AlarmforSch(context, id).cancelAlarm();
		}
		if(db !=null)
			db.close();
	}
	public void Update(ContentValues values,String id,int remind ,int repeat_type){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.update("Schedule", values, "id =?", new String[] { id });
		/**
		 * 设置提醒闹钟
		 */
		if(remind !=-1){//如果有提醒
			if(repeat_type !=0){//如果有重复
				new AlarmforSch(context, Integer.parseInt(id)).setRepeatAlarm();
			}else{//如果不重复
				new AlarmforSch(context, Integer.parseInt(id)).setAlarm();
			}
		}else{
			new AlarmforSch(context, Integer.parseInt(id)).cancelAlarm();
		}
		if (db != null)
			db.close();
	}

}
