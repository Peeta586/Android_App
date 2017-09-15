package com.example.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyTodoScheduleDB extends SQLiteOpenHelper {
	private static String name = "todo.db";
	private static int version = 1;

	public MyTodoScheduleDB(Context context) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/**
		 * 创建日程数据库表
		 */
		String sql = "create table Schedule(id integer not null primary key autoincrement,"
				+ "startDate DATETIME,startTime TIME,"
				+ "endDate DATETIME,endTime TIME,isfullday boolean,"
				+ "status INTEGER,remind integer,name varchar(1024),repeat_day INTEGER,"
				+ "repeat_week INTEGER,repeat_month INTEGER,repeat_type INTEGER,"
				+ "repeat_number INTEGER)";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
