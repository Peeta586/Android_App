package com.example.calDrawView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.example.util.Lunar;

/**
 * ������ʾ�õ�������
 * 
 * @author LSHM ˵���������ն�Ӧ����1����������Ӧ��7
 */
public class CalendarTools {

	// //��ȡũ����java��
	private Lunar lunar = new Lunar();
	private Calendar calendar = Calendar.getInstance();
	private CalendarVariable calv = new CalendarVariable();

	public final static String[] weekname = { "��", "һ", "��", "��", "��", "��", "��" };

	/**
	 * �޲͹��캯�����Ա�����ʼ��
	 */
	public CalendarTools() {

	}

	/**
	 * @param mode
	 *            0����ʾ����/�������µڼ��� 1����ʾ����/������������� 2����ʾ����/��������إ����
	 * @return
	 */
	public String GetCurLunar(int mode) {
		
		calendar.set(CalendarVariable.currentYear,
				CalendarVariable.currentMonth, CalendarVariable.TouchDay);
		lunar.setDate(calendar.getTime());
		String str = lunar.getCyclicaYear() + "/" + lunar.getAnimalString()
				+ "��" + lunar.getLunarMonthString() + "��";
		switch (mode) {
		case 0:
			str += "[" + calendar.get(Calendar.WEEK_OF_YEAR) + "��]";
			break;
		case 1:
			str += lunar.getCyclicaDay() + "��";
			break;
		case 2:
			str += lunar.getLunarDayString() + "��";
			break;
		}
		return str;
	}
//	public String[] getDays_lunar() {
//	return calv.days_lunar;
//}
//	public String[] getDays() {
//		return calv.days;
//	}

	public int getCurentYearR() {
		return calv.currentYearReal;
	}

	public int getCurrentMonthR() {
		return calv.currentMonthReal;
	}



	/**
	 * @param currentMonth
	 * @param currentYear
	 */
	public void SetTouchTime(int year, int month) {
		CalendarVariable.currentMonth = month;
		CalendarVariable.currentYear = year;
	}

	public int getCurrentYear() {
		return CalendarVariable.currentYear;
	}

	public int getCurrentMonth() {
		return CalendarVariable.currentMonth;
	}

	public void setCurrentYear(int year) {
		CalendarVariable.currentYear = year;
	}

	public void setCurrentMonth(int month) {
		CalendarVariable.currentMonth = month;
	}
	public int getHour(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.HOUR_OF_DAY);
	}
	public int getMinute(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MINUTE);
	}

	/**
	 * @param touchday
	 */
	public void SetTouchDay(int touchday) {
		CalendarVariable.TouchDay = touchday;
	}

	public int getTouchDay() {
		return CalendarVariable.TouchDay;
	}

	public int getCurrentDay() {
		return calv.currentDayReal;
	}

	public int getTouchLunarDay() {
		calendar.set(CalendarVariable.currentYear,
				CalendarVariable.currentMonth, CalendarVariable.TouchDay);
		lunar.setDate(calendar.getTime());
		return lunar.getLunarDay();
	
	}

	public int getCurrentLunarMonth() {
		calendar.set(CalendarVariable.currentYear,
				CalendarVariable.currentMonth, CalendarVariable.TouchDay);
		lunar.setDate(calendar.getTime());
		return lunar.getLunarMonth();
	}

	// ���ڻָ�Ϊ��ǰ���ڵĺ���
	public void SetReallyTime() {
		Calendar c =Calendar.getInstance();
		CalendarVariable.TouchDay = c.get(Calendar.DAY_OF_MONTH);
		CalendarVariable.currentMonth = c.get(Calendar.MONTH);
		CalendarVariable.currentYear = c.get(Calendar.YEAR);
	}

	/**
	 * ��������µõ����µ�����
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	protected int getMonthDays(int year, int month) {
		month++;// ת����ʵ���·�
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12: {
			return 31;
		}
		case 4:
		case 6:
		case 9:
		case 11: {
			return 30;
		}
		case 2: {
			if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
				return 29;
			else
				return 28;
		}
		default:
			return 0;
		}
	}

	/**
	 * @return��������������
	 */
	public int getMonthDays() {
		return calv.monthDays;
	}

	/**
	 * ���ݵ�ǰ�µõ�����ũ������
	 * 
	 * @return
	 */
	private int getMonthlunarDays(int year, int month, int day) {
		
		calendar.set(year, month, day);
		lunar.setDate(calendar.getTime());
		return lunar.getMaxDayInMonth();
	}

	/**
	 * �õ���������
	 * 
	 * @return
	 */
	public String[] getdayStrings(int year, int month) {
		String[] days = new String[getMonthDays(year, month)];
		for (int i = 0; i < days.length; i++) {
			days[i] = (i + 1) + "��";
		}
		return days;
	}

	/**
	 * �õ�����ũ������
	 * 
	 * @return
	 */
	public String[] getdayslunarsStrings(int year, int month, int day) {
		String[] days = new String[getMonthlunarDays(year, month, day)];
		for (int i = 0; i < days.length; i++) {
			days[i] = Lunar.getLunarDayString(i + 1);
		}
		return days;
	}

	/**
	 * ���ݵ�ǰ��ݣ��õ�����ũ���·ݣ� ������ʾwheelview���������
	 * 
	 * @return
	 */
	public String[] getMonthsOfLunar(int curYear) {
		String[] monthStrings = new String[] { "һ", "��", "��", "��", "��", "��",
				"��", "��", "��", "ʮ", "��", "��" };
		LinkedList<String> month = new LinkedList<String>();//�൱������
		month.add("һ��");
		month.add("����");
		month.add("����");
		month.add("����");
		month.add("����");
		month.add("����");
		month.add("����");
		month.add("����");
		month.add("����");
		month.add("ʮ��");
		month.add("����");
		month.add("����");
		// String[] month12 = new String[] { "һ��", "����", "����", "����", "����", "����",
		// "����", "����", "����", "ʮ��", "����", "����" };
		int lunarMonth = Lunar.getLunarLeapMonth(curYear);
		if (lunarMonth != 0)
			month.add(lunarMonth, "��" + monthStrings[lunarMonth - 1]);

		return month.toArray(new String[] {});
	}

	/*******����������ʱ���õĺ�����
	 * �ú�����ĳ��ĳ��ȫ�·��뵽days�����У����ҽ���Ӧ��ũ�����뵽day_lunar��
	 * 
	 * @param year
	 * @param month
	 * @param day
	 */
//	public void calculateDays() {
//		// ��������Ϊ����1��
//		calendar.set(CalendarVariable.currentYear,
//				CalendarVariable.currentMonth, 1);
//
//		calv.monthDays = getMonthDays(CalendarVariable.currentYear,
//				CalendarVariable.currentMonth);
//		// ���õ�ǰ�·�����
//		for (int d = 1, i = 0; d <= calv.monthDays; d++, i++) {
//			calv.days[i] = String.valueOf(d);
//			// ũ��
//			String textLunar = GetLunar(CalendarVariable.currentYear,
//					CalendarVariable.currentMonth, d);
//			calv.days_lunar[i] = textLunar;
//		}
//	}
	//private ScheduleOut scheduleOut = new ScheduleOut();
	public List<Map<String, String>> caList(){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		for(int i=0;i<GetFirstDayOfWeek()-1;i++){
			list.add(null);
		}
		calv.monthDays = getMonthDays(CalendarVariable.currentYear,
				CalendarVariable.currentMonth);
		// ���õ�ǰ�·�����
		for (int d = 1; d <= calv.monthDays; d++) {
			Map<String, String> map = new HashMap<String, String>();
			/**
			 * ����
			 */
			map.put("yangli", String.valueOf(d));
			/** ũ��
			 * 
			 */
			String textLunar = GetLunar(CalendarVariable.currentYear,
					CalendarVariable.currentMonth, d);
			map.put("nongli", textLunar);
			/**
			 * �Ƿ�Ϊ����
			 */
			if(isToday(d)){
				map.put("istoday", "1");
			}else{
				map.put("istoday", "0");
			}
			/**
			 * �����Ƿ��Ǽ���
			 */
			if(Holidays.equals("")){
				map.put("isholiday", "");
			}else{
				map.put("isholiday", Holidays);
			}
			list.add(map);
		}
		return list;
	}
	private boolean isToday(int day){
		Calendar cal =Calendar.getInstance();
		if(CalendarVariable.currentYear == cal.get(Calendar.YEAR)
				&& CalendarVariable.currentMonth == cal.get(Calendar.MONTH)
				&& day == cal.get(Calendar.DAY_OF_MONTH))
			return true;
		return false;
	}
	/**
	 * ���ݲ���ĳ��ĳ��ĳ�յõ���Ӧ��ũ��
	 * 
	 * @param day
	 * @return
	 */
	private String Holidays ="";
	public String GetLunar(int year, int month, int day) {
		// ����ũ��
		calendar.set(year, month, day);
		lunar.setDate(calendar.getTime());
		String textLunar = lunar.getLunarDayString();
		if (textLunar.equals("��һ")) {
			// �������������ڡ�����Ҫ��1����������תũ���·ݻ����С�»����
			calendar.set(year, month, day + 1);
			lunar.setDate(calendar.getTime());
			
			if (lunar.isBigMonth()) {
				textLunar = (lunar.getLunarMonthString().length() == 2) ? (lunar
						.getLunarMonthString() + "��") : (lunar
						.getLunarMonthString() + "�´�");
			} else {
				textLunar = (lunar.getLunarMonthString().length() == 2) ? (lunar
						.getLunarMonthString() + "С") : (lunar
						.getLunarMonthString() + "��С");
			}
			// �������������
			calendar.set(year, month, day);
			lunar.setDate(calendar.getTime());
		}
		
		if (lunar.isSFestival()) {//��������
			textLunar = lunar.getSFestivalName();
		}
		if (lunar.getTermString() == "") {// ����
		} else {
			textLunar = lunar.getTermString();
		}
		if (lunar.isLFestival()) {//ũ������
			textLunar = lunar.getLFestivalName();
		}
		if(lunar.isHoliday()){//�洢��һ���Ǽ���
			Holidays =String.valueOf(day); 
		}else
			Holidays ="";
		return textLunar;
	}
//	public String getHolidays() {
//		return Holidays;
//	}
//
//	public void setHolidays(String holidays) {
//		Holidays = holidays;
//	}

	/**
	 * @return�õ�������days�����еı��
	 */
	public int GetcurrentDayIndex() {
		// calendar.set(calv.currentYear, calv.currentMonth,1);
		// int monthDays = 0;//�õ��·�����
		// monthDays = getMonthDays(calv.currentYear, calv.currentMonth);
		int currentDayIndex = 0;
		for (int d = 1, i = 0; d <= calv.monthDays; d++, i++) {
			if (d == calv.currentDayReal) {
				currentDayIndex = i;
			}
		}
		return currentDayIndex;
	}

	/**
	 * @return�õ����µ�һ�����ܼ�
	 */
	public int GetFirstDayOfWeek() {
		calendar.set(CalendarVariable.currentYear,
				CalendarVariable.currentMonth, 1);
		// �������ܼ������������ն�Ӧ����1����������Ӧ��7
		int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
		return dayofweek;
	}

	/**
	 * @param year
	 * @param month
	 * @param day
	 * @return�õ������Ǳ���ڼ���
	 */
	public int GetWeekOfYear() {
		calendar.set(CalendarVariable.currentYear,
				CalendarVariable.currentMonth, 1);
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		return weekOfYear;

	}

	public int GetCurrentWeekOfYear() {
		calendar.set(CalendarVariable.currentYear,
				CalendarVariable.currentMonth, CalendarVariable.TouchDay);
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		return weekOfYear;
	}

	public int GetCurrentWeekOfYear(int year, int month, int day) {
		
		calendar.set(year, month, day);
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		return weekOfYear;
	}

	public void SetNextMonth() {
		CalendarVariable.currentMonth++;
		if (CalendarVariable.currentMonth > 11) {
			CalendarVariable.currentYear++;
			CalendarVariable.currentMonth = 0;
		}
		CalendarVariable.TouchDay = 1;
	}

	public void SetPreviousMonth() {
		CalendarVariable.currentMonth--;
		if (CalendarVariable.currentMonth < 0) {
			CalendarVariable.currentYear--;
			CalendarVariable.currentMonth = 11;
		}
		CalendarVariable.TouchDay = 1;
	}

	public String GetDayWeek() {
		calendar.set(CalendarVariable.currentYear,
				CalendarVariable.currentMonth, CalendarVariable.TouchDay);
		int dayweek = calendar.get(Calendar.DAY_OF_WEEK);
		return weekname[dayweek - 1];

	}

	public String GetDayWeek(int year, int month, int day) {
		
		calendar.set(year, month, day);
		int dayweek = calendar.get(Calendar.DAY_OF_WEEK);
		return weekname[dayweek - 1];

	}

	/**
	 * @param number
	 *            :��Ϊ����Ϊ����Ϊ����ʾ��ǰ�յ�ǰ|number|�죬Ϊ����ʾ��ǰ�յĺ�number�졣
	 *            ���ܣ�����number��ֵ��������ڵ�ǰ���ڵ�calendar�����ڡ�
	 */
	private static int[] firstdayofweek = new int[3];
	//private static int Currentdayofweek = -1;

	private void calculateWeek(int number) {
		int curYear = CalendarVariable.currentYear;
		int curMonth = CalendarVariable.currentMonth;
		int day = CalendarVariable.TouchDay;
		day += number;
		int monthdays = getMonthDays(curYear, curMonth);
		if (day <= 0) {
			curMonth--;
			if (curMonth < 0) {
				curYear--;
				curMonth = 11;
			}
			day = getMonthDays(curYear, curMonth) + day;
		} else if (day > monthdays) {
			curMonth++;
			if (curMonth > 11) {
				curYear++;
				curMonth = 0;
			}
			day = day - monthdays;
		}
		
		calendar.set(curYear, curMonth, day);
	}

	private String[] festival = new String[7];
	private static int Currentdayofweek = -1;
	public String[] getOneWeek() {
		String[] monthWeek = new String[7];
		
		calendar.set(CalendarVariable.currentYear,
				CalendarVariable.currentMonth, CalendarVariable.TouchDay);
		int curweek = calendar.get(Calendar.DAY_OF_WEEK);
		int num = 1 - curweek;
		// �趨�����ܵĵ�һ��
		for (int i = 0; i < monthWeek.length; i++) {
			calculateWeek(num + i);
			// ��¼���ܵ�һ�������ʱ��
			if (i == 0) {
				firstdayofweek[0] = calendar.get(Calendar.YEAR);
				firstdayofweek[1] = calendar.get(Calendar.MONTH);
				firstdayofweek[2] = calendar.get(Calendar.DAY_OF_MONTH);
				int betweendays = getDateToDatedays(calendar.getTimeInMillis());
				if(betweendays>=0 && betweendays<=6){
					Currentdayofweek =betweendays;
				}else{
					Currentdayofweek =-1;
				}
			}
			lunar.setDate(calendar.getTime());
			monthWeek[i] = String.format("%02d",
					calendar.get(Calendar.MONTH) + 1)
					+ "."
					+ String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
					+ "/"+lunar.getLunarMonthString()+"��"+lunar.getLunarDayString();
			/**
			 * �õ���������
			 */
			festival[i] = getFestivalString();
		}
		return monthWeek;
	}
	 /**
	 * @param Time
	 * @return:����Ҫ���ã���Ϊ��todayΪ������ʱ�����������������Ĳ�����Ϊ-0.��������Ϊ0��
	 * �ͳ������뱾�ܵ�һ���ֵһ�������
	 */
	private int getDateToDatedays(long Time){
		 Calendar calend1 =Calendar.getInstance();
		 long curtime=calend1.getTimeInMillis();
		 /**
		  * һ��ʼ����д�ģ�(int)( curtime- Time)/(1000*3600*24);
		  * �������Ƚ�curTime-Time�Ľ��ת��Ϊ��С��int�������������ֵ���ܵ��ͳ����˴���
		  */
		 if(curtime>Time){//�˴��й�����һ��Ҫ��( curtime- Time)/(1000*3600*24)�ȼ����ת��Ϊint��
			 //��������
			 return (int)(( curtime- Time)/(1000*3600*24));
		 }else{
			 return -1;
		 }
	 }
	public String[] getFestival() {
		return festival;
	}

	/**
	 * @param if_pre
	 *            ��Ϊtrue����һ�ܣ�Ϊfalse����һ��
	 */
	public void SetPre_NextWeek(boolean if_pre) {
		
		calendar.set(getCurrentYear(), getCurrentMonth(), getTouchDay());
		int curweek = calendar.get(Calendar.DAY_OF_WEEK);

		if (if_pre) {
			calculateWeek(1 - curweek - 7);
		} else {
			calculateWeek(7 - curweek + 1);
		}
		CalendarVariable.currentYear = calendar.get(Calendar.YEAR);
		CalendarVariable.currentMonth = calendar.get(Calendar.MONTH);
		CalendarVariable.TouchDay = calendar.get(Calendar.DAY_OF_MONTH);
	}

	public String getTianGanMonth() {
		
		calendar.set(CalendarVariable.currentYear,
				CalendarVariable.currentMonth, CalendarVariable.TouchDay);
		lunar.setDate(calendar.getTime());
		String str = lunar.getCyclicaMonth() + "��" + lunar.getCyclicaDay()
				+ "��";
		return str;
	}

	public void SetPreDay() {
		int curYear = CalendarVariable.currentYear;
		int curMonth = CalendarVariable.currentMonth;

		int day = CalendarVariable.TouchDay;
		day--;
		if (day <= 0) {
			curMonth--;
			if (curMonth < 0) {
				curYear--;
				curMonth = 11;
			}
			day = getMonthDays(curYear, curMonth);
		}
		CalendarVariable.currentYear = curYear;
		CalendarVariable.currentMonth = curMonth;
		CalendarVariable.TouchDay = day;
	}

	public void SetNextDay() {
		int curYear = CalendarVariable.currentYear;
		int curMonth = CalendarVariable.currentMonth;
		int monthdays = getMonthDays(CalendarVariable.currentYear,
				CalendarVariable.currentMonth);
		int day = CalendarVariable.TouchDay;
		day++;
		if (day > monthdays) {
			curMonth++;
			if (curMonth > 11) {
				curYear++;
				curMonth = 0;
			}
			day = 1;
		}
		CalendarVariable.currentYear = curYear;
		CalendarVariable.currentMonth = curMonth;
		CalendarVariable.TouchDay = day;
	}

	public String getFestivalString() {
		lunar.setDate(calendar.getTime());
		String sF = lunar.getSFestivalName();
		String lF = lunar.getLFestivalName();
		if (!sF.equals("")) {
			if (!lF.equals("")) {
				sF += "|" + lF;
				return sF;
			} else {
				return sF;
			}
		} else {
			return sF;
		}
	}

	public static int[] getFirstdayofweek() {
		return firstdayofweek;
	}

	public static int getCurrentdayofweek() {
		return Currentdayofweek;
	}

}
