package com.example.AddSchedule;

import java.util.Calendar;
import java.sql.Date;
import java.sql.Time;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;
import com.example.DataBase.MyScheduleDBUtil;
import com.example.calDrawView.CalendarVariable;
import com.example.mainsurface.R;

public class ScheduleIn {
	private Date startDate;
	private Time startTime;
	private Date endDate;
	private Time endTime;
	private boolean isfullday;
	private int status;
	private int remind;
	private String name;
	private int repeat_day;
	private int repeat_week;
	private int repeat_month;
	private int repeat_type;
	private int repeat_number;

	private Context context;
	/**
	 * 日程控件
	 */
	private EditText scheText;
	private TextView timeText;
	private TextView remindText;
	private TextView repeatText;

	private String[] arrayS = null;

	public ScheduleIn(Context context) {
		this.context = context;
		InitKongjian();// 初始化控件
		getScheContent();// 日程内容，2个
		getScheTime();// 日程时间，开始结束日期和时间。8个
		getScheRemind();// 提醒 1个
		getScheRepeat();// 重复 2个
	}

	private void InitKongjian() {
		scheText = (EditText) ((Activity) context)
				.findViewById(R.id.addschedittext);
		timeText = (TextView) ((Activity) context).findViewById(R.id.timeTx);
		remindText = (TextView) ((Activity) context)
				.findViewById(R.id.remindTx);
		repeatText = (TextView) ((Activity) context)
				.findViewById(R.id.repeatTx);
	}

	private void getScheContent() {
		name = scheText.getText().toString();
		status = 1;
	}

	private void getScheTime() {
		String schTimeStr = timeText.getText().toString();
		arrayS = schTimeStr.split(" ");
		String[] monday = arrayS[0].split("月");
		int month = Integer.parseInt(monday[0]) - 1;// 月份减一
		int day = Integer.parseInt(monday[1].split("日")[0]);

		repeat_month = month;
		repeat_day = day;
		repeat_week = getIndex(arrayS[1]);
		Calendar cal = Calendar.getInstance();
		//当前时间唯一性，存入endDate和endTIme中，实现导入导出是不重复导入。
		Calendar curCalendar =Calendar.getInstance();
		if (arrayS.length == 3) {// 不是全天
			isfullday = false;
			String[] arrayT = arrayS[2].split(":");// 时间
			int hour = Integer.parseInt(arrayT[0]);
			int second = Integer.parseInt(arrayT[1]);
			cal.set(CalendarVariable.getCurrentYear(), month, day, hour,
					second, 0);
			startDate = new Date(cal.getTime().getTime());
			startTime = new Time(cal.getTime().getTime());
			endDate = new Date(curCalendar.getTime().getTime());
			endTime = new Time(curCalendar.getTime().getTime());
		} else {// 是全天:设置时间为上午9:00
			isfullday = true;
			cal.set(CalendarVariable.getCurrentYear(), month, day);
			startDate = new Date(cal.getTime().getTime());
			cal.set(Calendar.HOUR_OF_DAY, 9);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			startTime = new Time(cal.getTime().getTime());
			endDate = new Date(curCalendar.getTime().getTime());
			endTime = new Time(curCalendar.getTime().getTime());
		}
	}

	/**
	 * 单位分钟
	 */
	private void getScheRemind() {
		String remindstr = remindText.getText().toString();
		if (remindstr.equals("不提醒") || remindstr.equals("")) {
			remind = -1;
		} else if (remindstr.equals("正点提醒")) {
			remind = 0;
		} else if (remindstr.equals("提前5分钟")) {
			remind = 5;
		} else if (remindstr.equals("提前10分钟")) {
			remind = 10;
		} else if (remindstr.equals("提前15分钟")) {
			remind = 15;
		} else if (remindstr.equals("提前30分钟")) {
			remind = 30;
		} else if (remindstr.equals("提前1小时")) {
			remind = 60;
		} else if (remindstr.equals("提前2小时")) {
			remind = 120;
		} else if (remindstr.equals("提前3小时")) {
			remind = 180;
		} else if (remindstr.equals("提前1天")) {
			remind = 1440;
		} else if (remindstr.equals("提前2天")) {
			remind = 2880;
		} else if (remindstr.equals("提前3天")) {
			remind = 4320;
		}
	}

	private void getScheRepeat() {
		String schRepeatStr = repeatText.getText().toString();
		if (schRepeatStr.equals("不重复") || schRepeatStr.equals("")) {
			repeat_type = 0;
			repeat_number = 0;
		} else if (schRepeatStr.equals("每天")) {
			repeat_type = 1;
			repeat_number = 1;
		} else if (schRepeatStr.equals("每周")) {
			repeat_type = 2;
			repeat_number = 1;
		} else if (schRepeatStr.equals("每两周")) {
			repeat_type = 2;
			repeat_number = 2;
		} else if (schRepeatStr.equals("每月")) {
			repeat_type = 3;
			repeat_number = 1;
		} else if (schRepeatStr.equals("每两月")) {
			repeat_type = 3;
			repeat_number = 2;
		} else if (schRepeatStr.equals("每三月")) {
			repeat_type = 3;
			repeat_number = 3;
		} else if (schRepeatStr.equals("每年")) {
			repeat_type = 4;
			repeat_number = 1;
		}
	}

	private int getIndex(String weekname) {
		String[] weeksStrings = new String[] { "", "周日", "周一", "周二", "周三",
				"周四", "周五", "周六" };
		for (int i = 1; i < weeksStrings.length; i++) {
			if (weeksStrings[i].equals(weekname)) {
				return i;
			}
		}
		return 0;
	}

	public void insert(){
		ContentValues contentValues = new ContentValues();
		contentValues.put("startDate", String.valueOf(startDate));
		contentValues.put("startTime", String.valueOf(startTime));
		contentValues.put("endDate", String.valueOf(endDate));
		contentValues.put("endTime", String.valueOf(endTime));
		contentValues.put("isfullday", isfullday);
		contentValues.put("status", status);
		contentValues.put("remind", remind);
		contentValues.put("name", name);
		contentValues.put("repeat_day", repeat_day);
		contentValues.put("repeat_week", repeat_week);
		contentValues.put("repeat_month", repeat_month);
		contentValues.put("repeat_type", repeat_type);
		contentValues.put("repeat_number", repeat_number);
		new MyScheduleDBUtil(context).Insert(contentValues, remind, repeat_type);
		
	}

	public void update(String id) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("startDate", String.valueOf(startDate));
		contentValues.put("startTime", String.valueOf(startTime));
		contentValues.put("endDate", String.valueOf(endDate));
		contentValues.put("endTime", String.valueOf(endTime));
		contentValues.put("isfullday", isfullday);
		contentValues.put("status", status);
		contentValues.put("remind", remind);
		contentValues.put("name", name);
		contentValues.put("repeat_day", repeat_day);
		contentValues.put("repeat_week", repeat_week);
		contentValues.put("repeat_month", repeat_month);
		contentValues.put("repeat_type", repeat_type);
		contentValues.put("repeat_number", repeat_number);
		new MyScheduleDBUtil(context).Update(contentValues, id, remind, repeat_type);
	}

}
