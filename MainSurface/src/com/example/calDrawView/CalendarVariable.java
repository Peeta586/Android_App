package com.example.calDrawView;

import java.util.Calendar;

public class CalendarVariable {
//	protected  String[] days =new String[31] ;
//	protected  String[] days_lunar = new String[31];
	
	private static  Calendar calendar = Calendar.getInstance();
	public static int currentYear =calendar.get(Calendar.YEAR);
	public static int currentMonth= calendar.get(Calendar.MONTH);
//	protected static int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
	protected final  int currentYearReal =calendar.get(Calendar.YEAR) ;
	protected final  int currentMonthReal=calendar.get(Calendar.MONTH) ;
	protected final  int currentDayReal = calendar.get(Calendar.DAY_OF_MONTH);
	//protected int hour =calendar.get(Calendar.HOUR_OF_DAY);
	//protected int minute = calendar.get(Calendar.MINUTE);
	
	//��ȡũ����java��
	protected  int monthDays = 0;//�õ��·�����
	
	protected static int TouchDay =calendar.get(Calendar.DAY_OF_MONTH);
	protected  String TouchYearl ="����";
	protected  String TouchMonthl ="��";
	//public static String TouchDayl =20;
	public static int getCurrentYear() {
		return currentYear;
	}
	public static int getCurrentMonth() {
		return currentMonth;
	}
	public static int getTouchDay() {
		return TouchDay;
	}

	
}
