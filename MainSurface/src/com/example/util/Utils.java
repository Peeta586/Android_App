package com.example.util;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressLint("SimpleDateFormat")
public class Utils {
	public static Calendar getTimeAfterInSecs(int secs) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, secs);
		return cal;
	}

	/**
	 * ���������ǰʱ��
	 * 
	 * @return
	 */
	public static Calendar getCurrentTime() {
		Calendar cal = Calendar.getInstance();
		
		return cal;
	}

	/**��ʱ���ַ������룬��ȡ���е�hour��secondȻ�������cal��
	 * @param TimeStr
	 * @return
	 */
	public static Calendar getTodayAt(String DateStr,String TimeStr) {
		String[] array = TimeStr.split(":");
		String[] array1 =DateStr.split("-");
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Integer.parseInt(array1[0]),
				Integer.parseInt(array1[1])-1,
				Integer.parseInt(array1[2]),Integer.parseInt(array[0]), Integer.parseInt(array[1]), 0);
		return cal;
	}

	public static String getDateTimeString(Calendar cal) {
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		df.setLenient(false);
		String s = df.format(cal.getTime());
		return s;
	}
}
