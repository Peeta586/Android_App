package com.example.ImportExport;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


import com.example.DataBase.MyTodoScheduleDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author LSHM
 *将数据库中的数据转化为list<Map<String,Object>>格式
 */
public class JsonService {
//	private Context context;
	private MyTodoScheduleDB helper = null;

	public JsonService(Context context) {
	//	this.context = context;
		helper = new MyTodoScheduleDB(context);
	}
	public List<JSONObject> getListScheduleJson() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from Schedule", null);
		List<JSONObject> maps = new ArrayList<JSONObject>();
		while (cursor.moveToNext()) {
			JSONObject map = new JSONObject();
			try {
				map.put("startDate",cursor.getString(cursor
							.getColumnIndex("startDate")));
				map.put("startTime",cursor.getString(cursor
						.getColumnIndex("startTime")));
				map.put("endDate",cursor.getString(cursor.getColumnIndex("endDate")));
				map.put("endTime",cursor.getString(cursor.getColumnIndex("endTime")));
				map.put("isfullday",cursor.getString(
						cursor.getColumnIndex("isfullday")).equals("0") ? false
						: true);
				map.put("status",cursor.getInt(cursor
						.getColumnIndex("status")));
				map.put("remind",cursor.getInt(cursor
						.getColumnIndex("remind")));
				map.put("name",cursor.getString(cursor
						.getColumnIndex("name")));
				map.put("repeat_day",cursor.getInt(cursor
						.getColumnIndex("repeat_day")));
				map.put("repeat_week",cursor.getInt(cursor
						.getColumnIndex("repeat_week")));
				map.put("repeat_month",cursor.getInt(cursor
						.getColumnIndex("repeat_month")));
				map.put("repeat_type",cursor.getInt(cursor
						.getColumnIndex("repeat_type")));
				map.put("repeat_number",cursor.getInt(cursor
						.getColumnIndex("repeat_number")));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			maps.add(map);
		}
		if(cursor != null)
			cursor.close();
		if (db != null) {
			db.close();
		}
		return maps;
	}
/**
 * 
//	public List<Map<String,Object>> getListScheduleJson() {
//		SQLiteDatabase db = helper.getReadableDatabase();
//		Cursor cursor = db.rawQuery("select * from Schedule", null);
//		List<Map<String,Object>> maps = new ArrayList<Map<String,Object>>();
//		while (cursor.moveToNext()) {
//			Map<String,Object> map = new HashMap<String, Object>();
//			map.put("startDate",cursor.getString(cursor
//						.getColumnIndex("startDate")));
//			
//			map.put("startTime",cursor.getString(cursor
//					.getColumnIndex("startTime")));
//			map.put("endDate",cursor.getString(cursor.getColumnIndex("endDate")));
//			map.put("endTime",cursor.getString(cursor.getColumnIndex("endTime")));
//			map.put("isfullday",cursor.getString(
//					cursor.getColumnIndex("isfullday")).equals("0") ? false
//					: true);
//			map.put("status",cursor.getInt(cursor
//					.getColumnIndex("status")));
//			map.put("remind",cursor.getInt(cursor
//					.getColumnIndex("remind")));
//			map.put("name",cursor.getString(cursor
//					.getColumnIndex("name")));
//			map.put("repeat_day",cursor.getInt(cursor
//					.getColumnIndex("repeat_day")));
//			map.put("repeat_week",cursor.getInt(cursor
//					.getColumnIndex("repeat_week")));
//			map.put("repeat_month",cursor.getInt(cursor
//					.getColumnIndex("repeat_month")));
//			map.put("repeat_type",cursor.getInt(cursor
//					.getColumnIndex("repeat_type")));
//			map.put("repeat_number",cursor.getInt(cursor
//					.getColumnIndex("repeat_number")));
//			
//			maps.add(map);
//		}
//		if(cursor != null)
//			cursor.close();
//		if (db != null) {
//			db.close();
//		}
//		return maps;
//	}
 * */

}
