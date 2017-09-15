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
	 * �ճ̿ؼ�
	 */
	private EditText scheText;
	private TextView timeText;
	private TextView remindText;
	private TextView repeatText;

	private String[] arrayS = null;

	public ScheduleIn(Context context) {
		this.context = context;
		InitKongjian();// ��ʼ���ؼ�
		getScheContent();// �ճ����ݣ�2��
		getScheTime();// �ճ�ʱ�䣬��ʼ�������ں�ʱ�䡣8��
		getScheRemind();// ���� 1��
		getScheRepeat();// �ظ� 2��
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
		String[] monday = arrayS[0].split("��");
		int month = Integer.parseInt(monday[0]) - 1;// �·ݼ�һ
		int day = Integer.parseInt(monday[1].split("��")[0]);

		repeat_month = month;
		repeat_day = day;
		repeat_week = getIndex(arrayS[1]);
		Calendar cal = Calendar.getInstance();
		//��ǰʱ��Ψһ�ԣ�����endDate��endTIme�У�ʵ�ֵ��뵼���ǲ��ظ����롣
		Calendar curCalendar =Calendar.getInstance();
		if (arrayS.length == 3) {// ����ȫ��
			isfullday = false;
			String[] arrayT = arrayS[2].split(":");// ʱ��
			int hour = Integer.parseInt(arrayT[0]);
			int second = Integer.parseInt(arrayT[1]);
			cal.set(CalendarVariable.getCurrentYear(), month, day, hour,
					second, 0);
			startDate = new Date(cal.getTime().getTime());
			startTime = new Time(cal.getTime().getTime());
			endDate = new Date(curCalendar.getTime().getTime());
			endTime = new Time(curCalendar.getTime().getTime());
		} else {// ��ȫ��:����ʱ��Ϊ����9:00
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
	 * ��λ����
	 */
	private void getScheRemind() {
		String remindstr = remindText.getText().toString();
		if (remindstr.equals("������") || remindstr.equals("")) {
			remind = -1;
		} else if (remindstr.equals("��������")) {
			remind = 0;
		} else if (remindstr.equals("��ǰ5����")) {
			remind = 5;
		} else if (remindstr.equals("��ǰ10����")) {
			remind = 10;
		} else if (remindstr.equals("��ǰ15����")) {
			remind = 15;
		} else if (remindstr.equals("��ǰ30����")) {
			remind = 30;
		} else if (remindstr.equals("��ǰ1Сʱ")) {
			remind = 60;
		} else if (remindstr.equals("��ǰ2Сʱ")) {
			remind = 120;
		} else if (remindstr.equals("��ǰ3Сʱ")) {
			remind = 180;
		} else if (remindstr.equals("��ǰ1��")) {
			remind = 1440;
		} else if (remindstr.equals("��ǰ2��")) {
			remind = 2880;
		} else if (remindstr.equals("��ǰ3��")) {
			remind = 4320;
		}
	}

	private void getScheRepeat() {
		String schRepeatStr = repeatText.getText().toString();
		if (schRepeatStr.equals("���ظ�") || schRepeatStr.equals("")) {
			repeat_type = 0;
			repeat_number = 0;
		} else if (schRepeatStr.equals("ÿ��")) {
			repeat_type = 1;
			repeat_number = 1;
		} else if (schRepeatStr.equals("ÿ��")) {
			repeat_type = 2;
			repeat_number = 1;
		} else if (schRepeatStr.equals("ÿ����")) {
			repeat_type = 2;
			repeat_number = 2;
		} else if (schRepeatStr.equals("ÿ��")) {
			repeat_type = 3;
			repeat_number = 1;
		} else if (schRepeatStr.equals("ÿ����")) {
			repeat_type = 3;
			repeat_number = 2;
		} else if (schRepeatStr.equals("ÿ����")) {
			repeat_type = 3;
			repeat_number = 3;
		} else if (schRepeatStr.equals("ÿ��")) {
			repeat_type = 4;
			repeat_number = 1;
		}
	}

	private int getIndex(String weekname) {
		String[] weeksStrings = new String[] { "", "����", "��һ", "�ܶ�", "����",
				"����", "����", "����" };
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
