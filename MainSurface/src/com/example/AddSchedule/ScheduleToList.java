package com.example.AddSchedule;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.DataBase.MyTodoScheduleDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ScheduleToList {
	private Context context;
	private Date date;
	private MyTodoScheduleDB helper = null;
	private List<List<Map<String, Object>>> lists=null;
	private final static int DAY_SCHNUM =5;
	public ScheduleToList(Context context ,Date date){
		this.context= context;
		this.date =date;
		helper = new MyTodoScheduleDB(context);
	}
	
	/**根据Date获得日程信息，包括name，和startTime，id信息，
	 * List<List<Map<String, Object>>>形式
	 * @return
	 */
	public List<List<Map<String, Object>>> setScheduleInLinear(){
		ScheduleOut out = new ScheduleOut(context);
		String str = out.getDbIdByCurDate(date);
		String[] array = str.split(",");
		SQLiteDatabase db = helper.getReadableDatabase();
		/**
		 * 实例化lists，然后初始化每一项为null
		 */
		lists =new ArrayList< List<Map<String,Object>>>();
		for(int i =0;i<DAY_SCHNUM;i++){//初始化list
			lists.add(i, null);
		}
		//List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < array.length; i++) {
			if (!array[i].equals("")) {
				Cursor cursor = db.rawQuery("select* from Schedule where id ="
						+ (int)Integer.parseInt(array[i]), null);
				while(cursor.moveToNext()){
					Log.i("sche","-->id"+array[i]);
					String timeS=cursor.getString(cursor.getColumnIndex("startTime"));
					String isfull =cursor.getString(cursor.getColumnIndex("isfullday"));
					boolean isfullday = isfull.equals("1") ? true : false;
					Log.i("sche","-->startTime"+timeS);
					List<Map<String, Object>> list = getListMapByTime(timeS,isfullday);
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("id", Integer.parseInt(array[i]));
					map.put("name", cursor.getString(cursor.getColumnIndex("name")));
					map.put("startTime", timeS);
					Log.i("sche","-->map--"+array[i]+map.get("name")+map.get("startTime"));
					//map.put("is", isfullday);
					list.add(map);
				}
				cursor.close();
			}
		}
		if(db !=null){
			db.close();
		}
		return lists;
	}
	/**注意lists不用add（int location,List<Map<String,Object>>）
	 * 而是用set（int location,List<Map<String,Object>>）
	 * 官方解释：
	 * 1.set(int location, E object)：Replaces the element at the specified location in this List
	 *  with the specified object.
	 *
	 * 2.add(int location, E object)：Inserts the specified object into this List at the specified location.
	 * 1）add的解释：Inserts the specified object into this List at the specified location. 
	 * The object is inserted before the current element at the specified location. 
	 * If the location is equal to the size of this List, the object is added at the end. 
	 * 2）原因所在：If the location is smaller than the size of this List,
	 *  then all elements beyond the specified location are moved by one position towards the end of the List.
	 * 我觉得应该是已经初始化lists大小为5，每个单元为null的List了，所以只需要set就可以了，而不用add
	 * @param startTime
	 * @param isfullday
	 * @return
	 */
	private List<Map<String, Object>> getListMapByTime(String startTime,boolean isfullday){
		//Time time = new Time(startTime);
		if(isfullday){
			if(lists.get(0) == null){
				List<Map<String, Object>> map = new ArrayList<Map<String,Object>>();
				lists.set(0, map);
				Log.i("sche","-->map0 quantian null");
			}
			Log.i("sche","-->map0 quantian");
			return lists.get(0);
		}else{
			if(startTime.compareTo("00:00:00")>=0 && startTime.compareTo("07:00:00")<=0){//凌晨
				if(lists.get(1) == null){
					List<Map<String, Object>> map = new ArrayList<Map<String,Object>>();
					lists.set(1, map);
					Log.i("sche","-->map1 00-07 null");
				}
				Log.i("sche","-->map1 00-07");
				return lists.get(1);
			}else if(startTime.compareTo("07:00:00")>0 && startTime.compareTo("12:00:00")<=0){//上午
				if(lists.get(2) == null){
					List<Map<String, Object>> map = new ArrayList<Map<String,Object>>();
					lists.set(2, map);
					Log.i("sche","-->map2 7-12 null");
				}
				Log.i("sche","-->map2 7-12");
				return lists.get(2);
			}else if(startTime.compareTo("12:00:00")>0 && startTime.compareTo("18:00:00")<=0){//下午
				if(lists.get(3) == null){
					List<Map<String, Object>> map = new ArrayList<Map<String,Object>>();
					lists.set(3, map);
					Log.i("sche","-->map3 12-18 null");
				}
				Log.i("sche","-->map3 12-18");
				return lists.get(3);
			}else if(startTime.compareTo("18:00:00")>0 && startTime.compareTo("23:59:59")<=0){//晚上
				if(lists.get(4) == null){
					List<Map<String, Object>> map = new ArrayList<Map<String,Object>>();
					lists.set(4, map);
					Log.i("sche","-->map4 18-23 null");
				}
				Log.i("sche","-->map4 18-23");
				return lists.get(4);
			}
		}
		return null;
	}
	public List<Map<String, Object>> setScheduleInWeek(){
		ScheduleOut out = new ScheduleOut(context);
		String str = out.getDbIdByCurDate(date);
		String[] array = str.split(",");
		SQLiteDatabase db = helper.getReadableDatabase();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < array.length; i++) {
			if (!array[i].equals("")) {
				Cursor cursor = db.rawQuery("select* from Schedule where id ="
						+ (int)Integer.parseInt(array[i]), null);
				while(cursor.moveToNext()){
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("startTime", cursor.getString(cursor.getColumnIndex("startTime")));
					map.put("name", cursor.getString(cursor.getColumnIndex("name")));
					list.add(map);
				}
				cursor.close();
			}
		}
		if(db !=null){
			db.close();
		}
		if(list.size()<=0){
			return null;
		}else
			return list;
	}
	public List<Map<String, Object>> setScheduleInMonth(){
		ScheduleOut out = new ScheduleOut(context);
		String str = out.getDbIdByCurDate(date);
		String[] array = str.split(",");
		SQLiteDatabase db = helper.getReadableDatabase();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < array.length; i++) {
			if (!array[i].equals("")) {
				Cursor cursor = db.rawQuery("select* from Schedule where id ="
						+ (int)Integer.parseInt(array[i]), null);
				while(cursor.moveToNext()){
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("id", cursor.getString(cursor.getColumnIndex("id")));
					map.put("startTime", cursor.getString(cursor.getColumnIndex("startTime")));
					map.put("name", cursor.getString(cursor.getColumnIndex("name")));
					list.add(map);
				}
				cursor.close();
			}
		}
		if(db !=null){
			db.close();
		}
		if(list.size()<=0){
			return null;
		}else
			return list;
	}
	
}
