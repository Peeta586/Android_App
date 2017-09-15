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
		 * ��������
		 */
		Cursor cursor=db.query("Schedule", new String[]{"id"}, null, null, null, null, "id desc");
		int id =0;
		//�����ǰ����id��Ҳ���Ǹմ����id
		if(cursor.moveToNext()){
			id =cursor.getInt(cursor.getColumnIndex("id"));
		}
		if(cursor !=null)
			cursor.close();
		//�õ�id������id�������塣
		if(remind !=-1){//���������
			if(repeat_type !=0){//������ظ�
				new AlarmforSch(context, id).setRepeatAlarm();
			}else{//������ظ�
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
		 * ������������
		 */
		if(remind !=-1){//���������
			if(repeat_type !=0){//������ظ�
				new AlarmforSch(context, Integer.parseInt(id)).setRepeatAlarm();
			}else{//������ظ�
				new AlarmforSch(context, Integer.parseInt(id)).setAlarm();
			}
		}else{
			new AlarmforSch(context, Integer.parseInt(id)).cancelAlarm();
		}
		if (db != null)
			db.close();
	}

}
