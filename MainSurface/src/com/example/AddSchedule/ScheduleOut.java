package com.example.AddSchedule;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.DataBase.MyTodoScheduleDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**֪ʶ�㣺����cursor����ܹر�db����������
 * @author LSHM
 *
 */
public class ScheduleOut {
	// private Context context;
	private MyTodoScheduleDB helper;
	private SQLiteDatabase db =null;
	public ScheduleOut(Context context) {
		// this.context = context;
		helper = new MyTodoScheduleDB(context);
	}

	/**
	 * @param date
	 * @return String�������ݣ�����,������id��ɵ� ������ݿⲻ���ڷ��ؿ��ַ���
	 */
	public String getDbIdByCurDate(Date date) {
		String str = "";
		/**
		 * ���������ճ�
		 */
		if (helper != null) {
			Cursor cursor = query1(date);
			if(cursor !=null){
				while (cursor.moveToNext()) {
					str += cursor.getString(cursor.getColumnIndex("id")) + ",";
	
				}
				cursor.close();
				if(db !=null)
					db.close();
			}
			Cursor cursor2 = query2(date);
			if(cursor2 != null){
				str += getStringByCursor(cursor2, date);
				cursor2.close();
				if(db !=null)
					db.close();
			}
		}
		return str;
	}
//	public int getCountByDate(Date date){
//		int numSch=0;
//		if (helper != null) {
//			Cursor cursor = query1(date);
//			if(cursor !=null){
//				numSch +=cursor.getCount();
//				cursor.close();
//				if(db !=null)
//					db.close();
//			}
//			Cursor cursor2 = query2(date);
//			if(cursor2 != null){
//				numSch +=cursor2.getCount();
//				cursor2.close();
//				if(db !=null)
//					db.close();
//			}
//		}
//		
//		return numSch;
//	}

	/**
	 * @param date
	 * @return ����������ѯ��������Ϊ��ʼ���ճ�
	 */
	private Cursor query1(Date date) {
		db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from Schedule where startDate ='"
				+ date + "'", null);
//�����ڴ˴��ر�db���ᵼ��cursor����
//		if(db !=null)
//			db.close();
		return cursor;
	}

	/**
	 * @param date
	 * @return ����������ѯ������ǰ�������ճ������ظ���������
	 */
	private Cursor query2(Date date) {
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("Schedule", new String[] { "*" },
				"startDate <'" + date + "'", null, null, null, "startTime");
		// db.rawQuery("select * from Schedule where startDate < '"
		// + date+"'", null);
		if(cursor.getCount() ==0){
			return null;
		}
		return cursor;
	}

	/**
	 * @param cursor
	 * @param date
	 *            :Ҫ��ѯ�����ڣ�
	 * @return
	 */
	private String getStringByCursor(Cursor cursor, Date date) {
		String str = "";
		while (cursor.moveToNext()) {
			String startDate = cursor.getString(cursor
					.getColumnIndex("startDate"));
			int repeat_type = cursor.getInt(cursor
					.getColumnIndex("repeat_type"));
			switch (repeat_type) {
			case 1:
				str += cursor.getInt(cursor.getColumnIndex("id")) + ",";
				break;
			case 2:
				int num_week = cursor.getInt(cursor
						.getColumnIndex("repeat_number"));
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				int week = cal.get(Calendar.DAY_OF_WEEK);
				int repeat_week = cursor.getInt(cursor
						.getColumnIndex("repeat_week"));
				if (num_week == 1) {
					if (week == repeat_week) {
						str += cursor.getInt(cursor.getColumnIndex("id")) + ",";
					}
				} else if (num_week == 2) {
					if ((week == repeat_week)
							&& getDateToDatedays(date, startDate)) {
						str += cursor.getInt(cursor.getColumnIndex("id")) + ",";
					}
				}
				break;
			case 3:
				int num_month = cursor.getInt(cursor
						.getColumnIndex("repeat_number"));
				int repeat_day = cursor.getInt(cursor
						.getColumnIndex("repeat_day"));
				int repeat_month = cursor.getInt(cursor
						.getColumnIndex("repeat_month"));
				Calendar cal3 = Calendar.getInstance();
				cal3.setTime(date);
				int day = cal3.get(Calendar.DAY_OF_MONTH);
				int month = cal3.get(Calendar.MONTH);
				if (num_month == 1) {// һ��
					if (day == repeat_day && month > repeat_month) {
						str += cursor.getInt(cursor.getColumnIndex("id")) + ",";
					}
				} else if (num_month == 2) {// ����
					if (day == repeat_day && (month - repeat_month) % 2 == 0) {
						str += cursor.getInt(cursor.getColumnIndex("id")) + ",";
					}
				} else if (num_month == 3) {// ����
					if (day == repeat_day && (month - repeat_month) % 3 == 0) {
						str += cursor.getInt(cursor.getColumnIndex("id")) + ",";
					}
				}
				break;
			case 4:
				Calendar cal4 = Calendar.getInstance();
				cal4.setTime(date);
				int year = cal4.get(Calendar.YEAR);
				int month4 = cal4.get(Calendar.MONTH);
				int day4 = cal4.get(Calendar.DAY_OF_MONTH);
				int repeat_day4 = cursor.getInt(cursor
						.getColumnIndex("repeat_day"));
				int repeat_month4 = cursor.getInt(cursor
						.getColumnIndex("repeat_month"));
				int repeat_year = Integer.parseInt(startDate.split("-")[0]);
				if (day4 == repeat_day4 && month4 == repeat_month4
						&& year > repeat_year) {
					str += cursor.getInt(cursor.getColumnIndex("id")) + ",";
				}
				break;
			}
		}
		return str;
	}

	// @SuppressLint("SimpleDateFormat")
	private boolean getDateToDatedays(Date date, String startTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		try {
			Date date1 = new Date(fmt.parse(startTime).getTime());
			if (date1 != null) {
				long DateTime = date.getTime();
				long startDate = date1.getTime();
				if (DateTime > startDate) {
					int daynum = (int) (DateTime - startDate)
							/ (1000 * 3600 * 24);
					if (daynum % 14 == 0)
						return true;
					else
						return false;
				} else {
					return false;
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
}
