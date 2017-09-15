package com.example.Alarm;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.DataBase.MyTodoScheduleDB;
import com.example.util.Utils;

/**
 * @author LSHM
 * 
 */
public class AlarmforSch {
	private Context context;
	private int id;
	private String startDate;
	private String startTime;
	private int remind;
	private int repeat_type;
	private int repeat_num;
	private long Interval_num;
	private String name;
	private MyTodoScheduleDB helper = null;

	public AlarmforSch(Context context, int id) {
		this.context = context;
		this.id = id;
		helper = new MyTodoScheduleDB(context);
		getColums();
	}

	private void getColums() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from Schedule where id=" + id,
				null);
		while (cursor.moveToNext()) {
			startDate = cursor.getString(cursor.getColumnIndex("startDate"));
			startTime = cursor.getString(cursor.getColumnIndex("startTime"));
			name = cursor.getString(cursor.getColumnIndex("name"));
			remind = cursor.getInt(cursor.getColumnIndex("remind"));
			repeat_type = cursor.getInt(cursor.getColumnIndex("repeat_type"));
			repeat_num = cursor.getInt(cursor.getColumnIndex("repeat_number"));
		}
		// 用完后记得要关闭这两个工具
		if (cursor != null)
			cursor.close();
		if (db != null) {
			db.close();
		}
	}

	public void setAlarm() {
		sendAlarm(id);
		Log.i("nao", "setAlarm");
	}

	public void setRepeatAlarm() {
		Interval_num = getInterval();
		sendRepeatingAlarm(id, Interval_num);
		Log.i("nao", "setAlarmRepeat");
	}

	public void cancelAlarm() {
		cancelRepeatingAlarm(id);
	}

	/**
	 * @return:日程提醒的日期时间
	 */
	private Calendar getAlarmCalendar() {
		Calendar cal = Utils.getTodayAt(startDate, startTime);
		Log.i("nao", "year->" + cal.get(Calendar.YEAR));
		Log.i("nao", "month->" + cal.get(Calendar.MONTH));
		Log.i("nao", "day->" + cal.get(Calendar.DAY_OF_MONTH));
		Log.i("nao", "hour->" + cal.get(Calendar.HOUR_OF_DAY));
		Log.i("nao", "minute->" + cal.get(Calendar.MINUTE));
		Log.i("nao", "remind->" + String.valueOf(remind));
		long realcal = cal.getTimeInMillis() - remind * 1000 * 60;
		Log.i("nao", "calTimeMillis->" + cal.getTimeInMillis());
		Calendar cal1 =Calendar.getInstance();
		Log.i("nao", "cal1->" + cal1.getTimeInMillis());
		Log.i("nao", "realcal->" + realcal);
		cal.setTimeInMillis(realcal);
		return cal;
	}

	private long getInterval() {
		long interval = 0;
		switch (repeat_type) {
		case 1:// 天的
			interval = 1000 * 3600 * 24;// 一天的时间，毫秒为单位
			break;
		case 2:// 周的
			interval = 1000 * 3600 * 24 * 7 * repeat_num;
			break;
		case 3:// 月的，月份的天数固定值为30天，需要改进的为动态设置
			interval = 1000 * 3600 * 24 * 30 * repeat_num;
			break;
		case 4:// 年的
			interval = 1000 * 3600 * 24 * 365;
			break;
		}
		return interval;
	}

	/**
	 * 不重复的闹钟
	 * 
	 * @param requestCode
	 */
	private void sendAlarm(int requestCode) {
		Calendar cal = getAlarmCalendar();
		
		String s = Utils.getDateTimeString(cal);
		Intent intent = new Intent(context, RemindReceiver.class);
		intent.putExtra("name", name);
		intent.putExtra("time", s);
		intent.putExtra("id", id);

		PendingIntent pi = this.getDistinctPendingIntent(intent, requestCode);
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
		
	}

	/**
	 * 重复闹钟的设置
	 * 
	 * @param requestCode
	 */
	private void sendRepeatingAlarm(int requestCode, long Interval_num) {
		Calendar cal = getAlarmCalendar();
		Log.i("nao", "cal.getTimeInMillis()->" + cal.getTimeInMillis());
		String s = Utils.getDateTimeString(cal);
		Intent intent = new Intent(context, RemindReceiver.class);
		intent.putExtra("name", "nihao");
		intent.putExtra("time", s);
		intent.putExtra("id", id);
		PendingIntent pi = this.getDistinctPendingIntent(intent, requestCode);
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
				Interval_num, pi);

	}

	private void cancelRepeatingAlarm(int requestCode) {
		Intent intent = new Intent(context, RemindReceiver.class);
		PendingIntent pI = this.getDistinctPendingIntent(intent, requestCode);
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		am.cancel(pI);
	}

	protected PendingIntent getDistinctPendingIntent(Intent intent,
			int requestId) {
		PendingIntent pIntent = PendingIntent.getBroadcast(context, requestId,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		return pIntent;
	}
}
