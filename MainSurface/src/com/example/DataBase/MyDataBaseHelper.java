package com.example.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBaseHelper extends SQLiteOpenHelper {
	private static String name = "weather.db";
	private static int version = 1;

	public MyDataBaseHelper(Context context) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)创建数据库表
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		/**
		 * 创建未来天气表：Future，今日状况表：Today，生活指数表：Index
		 */
		String sql = "create table Future(data_time varchar(10),weather varchar(20),temp varchar(12),fengli varchar(40),icon1 varchar(8),icon2 varchar(8),city_id varchar(10))";
		db.execSQL(sql);
		sql = "create table Today(update_time varchar(20),temp varchar(6),fengli varchar(40),shidu varchar(6),air_q varchar(30),ziwai varchar(10),city_id varchar(10))";
		db.execSQL(sql);
		sql = "create table IndexTable(chuanyi varchar(80),guomin varchar(80),yundong varchar(80),xiche varchar(80),liang varchar(80),lvyou varchar(80),lukuang varchar(80),shushi varchar(80),kongqi varchar(80),ziwai varchar(80),city_id varchar(10))";
		db.execSQL(sql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
