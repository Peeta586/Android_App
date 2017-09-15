package com.example.calDrawView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.example.util.Lunar;

/**
 * 日历显示用的日历类
 * 
 * @author LSHM 说明：星期日对应的是1，星期六对应着7
 */
public class CalendarTools {

	// //获取农历的java类
	private Lunar lunar = new Lunar();
	private Calendar calendar = Calendar.getInstance();
	private CalendarVariable calv = new CalendarVariable();

	public final static String[] weekname = { "日", "一", "二", "三", "四", "五", "六" };

	/**
	 * 无餐构造函数，对变量初始化
	 */
	public CalendarTools() {

	}

	/**
	 * @param mode
	 *            0：显示甲午/马年三月第几周 1：显示甲午/马年三月天干日 2：显示甲午/马年三月廿四日
	 * @return
	 */
	public String GetCurLunar(int mode) {
		
		calendar.set(CalendarVariable.currentYear,
				CalendarVariable.currentMonth, CalendarVariable.TouchDay);
		lunar.setDate(calendar.getTime());
		String str = lunar.getCyclicaYear() + "/" + lunar.getAnimalString()
				+ "年" + lunar.getLunarMonthString() + "月";
		switch (mode) {
		case 0:
			str += "[" + calendar.get(Calendar.WEEK_OF_YEAR) + "周]";
			break;
		case 1:
			str += lunar.getCyclicaDay() + "日";
			break;
		case 2:
			str += lunar.getLunarDayString() + "日";
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

	// 用于恢复为当前日期的函数
	public void SetReallyTime() {
		Calendar c =Calendar.getInstance();
		CalendarVariable.TouchDay = c.get(Calendar.DAY_OF_MONTH);
		CalendarVariable.currentMonth = c.get(Calendar.MONTH);
		CalendarVariable.currentYear = c.get(Calendar.YEAR);
	}

	/**
	 * 根据年和月得到当月的天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	protected int getMonthDays(int year, int month) {
		month++;// 转换到实际月份
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
	 * @return：本月阳历天数
	 */
	public int getMonthDays() {
		return calv.monthDays;
	}

	/**
	 * 根据当前月得到本月农历天数
	 * 
	 * @return
	 */
	private int getMonthlunarDays(int year, int month, int day) {
		
		calendar.set(year, month, day);
		lunar.setDate(calendar.getTime());
		return lunar.getMaxDayInMonth();
	}

	/**
	 * 得到阳历的天
	 * 
	 * @return
	 */
	public String[] getdayStrings(int year, int month) {
		String[] days = new String[getMonthDays(year, month)];
		for (int i = 0; i < days.length; i++) {
			days[i] = (i + 1) + "日";
		}
		return days;
	}

	/**
	 * 得到本月农历的天
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
	 * 根据当前年份，得到本年农历月份， 用于显示wheelview滚轴的数据
	 * 
	 * @return
	 */
	public String[] getMonthsOfLunar(int curYear) {
		String[] monthStrings = new String[] { "一", "二", "三", "四", "五", "六",
				"七", "八", "九", "十", "冬", "腊" };
		LinkedList<String> month = new LinkedList<String>();//相当于链表
		month.add("一月");
		month.add("二月");
		month.add("三月");
		month.add("四月");
		month.add("五月");
		month.add("六月");
		month.add("七月");
		month.add("八月");
		month.add("九月");
		month.add("十月");
		month.add("冬月");
		month.add("腊月");
		// String[] month12 = new String[] { "一月", "二月", "三月", "四月", "五月", "六月",
		// "七月", "八月", "九月", "十月", "冬月", "腊月" };
		int lunarMonth = Lunar.getLunarLeapMonth(curYear);
		if (lunarMonth != 0)
			month.add(lunarMonth, "闰" + monthStrings[lunarMonth - 1]);

		return month.toArray(new String[] {});
	}

	/*******画布日历的时候用的函数，
	 * 该函数将某年某月全月放入到days数组中，并且将对应的农历放入到day_lunar中
	 * 
	 * @param year
	 * @param month
	 * @param day
	 */
//	public void calculateDays() {
//		// 将日期设为本月1号
//		calendar.set(CalendarVariable.currentYear,
//				CalendarVariable.currentMonth, 1);
//
//		calv.monthDays = getMonthDays(CalendarVariable.currentYear,
//				CalendarVariable.currentMonth);
//		// 设置当前月份日期
//		for (int d = 1, i = 0; d <= calv.monthDays; d++, i++) {
//			calv.days[i] = String.valueOf(d);
//			// 农历
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
		// 设置当前月份日期
		for (int d = 1; d <= calv.monthDays; d++) {
			Map<String, String> map = new HashMap<String, String>();
			/**
			 * 阳历
			 */
			map.put("yangli", String.valueOf(d));
			/** 农历
			 * 
			 */
			String textLunar = GetLunar(CalendarVariable.currentYear,
					CalendarVariable.currentMonth, d);
			map.put("nongli", textLunar);
			/**
			 * 是否为今天
			 */
			if(isToday(d)){
				map.put("istoday", "1");
			}else{
				map.put("istoday", "0");
			}
			/**
			 * 今天是否是假日
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
	 * 根据参数某年某月某日得到对应的农历
	 * 
	 * @param day
	 * @return
	 */
	private String Holidays ="";
	public String GetLunar(int year, int month, int day) {
		// 设置农历
		calendar.set(year, month, day);
		lunar.setDate(calendar.getTime());
		String textLunar = lunar.getLunarDayString();
		if (textLunar.equals("初一")) {
			// 再重新设置日期。这里要加1，否则阳历转农历月份会错，大小月会出错
			calendar.set(year, month, day + 1);
			lunar.setDate(calendar.getTime());
			
			if (lunar.isBigMonth()) {
				textLunar = (lunar.getLunarMonthString().length() == 2) ? (lunar
						.getLunarMonthString() + "大") : (lunar
						.getLunarMonthString() + "月大");
			} else {
				textLunar = (lunar.getLunarMonthString().length() == 2) ? (lunar
						.getLunarMonthString() + "小") : (lunar
						.getLunarMonthString() + "月小");
			}
			// 将日期再设回来
			calendar.set(year, month, day);
			lunar.setDate(calendar.getTime());
		}
		
		if (lunar.isSFestival()) {//阳历节日
			textLunar = lunar.getSFestivalName();
		}
		if (lunar.getTermString() == "") {// 节气
		} else {
			textLunar = lunar.getTermString();
		}
		if (lunar.isLFestival()) {//农历节日
			textLunar = lunar.getLFestivalName();
		}
		if(lunar.isHoliday()){//存储那一天是假日
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
	 * @return得到今天在days数组中的标号
	 */
	public int GetcurrentDayIndex() {
		// calendar.set(calv.currentYear, calv.currentMonth,1);
		// int monthDays = 0;//得到月份天数
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
	 * @return得到本月第一天是周几
	 */
	public int GetFirstDayOfWeek() {
		calendar.set(CalendarVariable.currentYear,
				CalendarVariable.currentMonth, 1);
		// 今天是周几？——星期日对应的是1，星期六对应着7
		int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
		return dayofweek;
	}

	/**
	 * @param year
	 * @param month
	 * @param day
	 * @return得到今天是本年第几周
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
	 *            :可为正可为负，为负表示当前日的前|number|天，为正表示当前日的后number天。
	 *            功能：根据number的值设置相对于当前日期的calendar的日期。
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
		// 设定到本周的第一天
		for (int i = 0; i < monthWeek.length; i++) {
			calculateWeek(num + i);
			// 记录本周第一天的日期时间
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
					+ "/"+lunar.getLunarMonthString()+"月"+lunar.getLunarDayString();
			/**
			 * 得到节日数组
			 */
			festival[i] = getFestivalString();
		}
		return monthWeek;
	}
	 /**
	 * @param Time
	 * @return:条件要卡好，因为当today为周六的时候，下周周日与周六的差距可能为-0.几。导致为0。
	 * 就出现了与本周第一天的值一样的情况
	 */
	private int getDateToDatedays(long Time){
		 Calendar calend1 =Calendar.getInstance();
		 long curtime=calend1.getTimeInMillis();
		 /**
		  * 一开始这样写的：(int)( curtime- Time)/(1000*3600*24);
		  * 这样会先将curTime-Time的结果转化为较小的int，这样如果两个值差距很到就出现了错误。
		  */
		 if(curtime>Time){//此处有过错误，一定要将( curtime- Time)/(1000*3600*24)先计算后转化为int，
			 //否则会出错
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
	 *            ：为true：上一周，为false：下一周
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
		String str = lunar.getCyclicaMonth() + "月" + lunar.getCyclicaDay()
				+ "日";
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
