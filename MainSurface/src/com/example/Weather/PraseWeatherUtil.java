package com.example.Weather;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.DataBase.MyDataBaseHelper;
import com.example.mainsurface.R;


public class PraseWeatherUtil {
	private String province0;//所在省市
	private String city_text1;//南京
	private String province_id2;//1944
	private String date_time3;
	private String today_weather4;
	private String first_qiwen5;
	private String first_fengxiang6;
	private String first_date7;
	//private String first_qiwen8;
	private String first_feng9;
	private int first_icon110;
	private int first_icon211;
	private String first_shudu;
	private String first_kongqi;
	private String first_ziwai;
	private String chuanyi_index;
	private String guomin_index;
	private String yundong_index;
	private String xiche_index;
	private String liangshai_index;
	private String lvyou_index;
	private String lukuang_index;
	private String shushidu_index;
	private String kongqi_index;
	private String ziwai_index;
	
	private String second_date;
	private String second_weather;
	//private String second_temp;
	private String second_feng;
	private int second_icon1;
	private int second_icon2;
	
	private String third_date;
	private String thrid_weather;
	//private String thrid_temp;
	private String thrid_feng;
	private int thrid_icon1;
	private int thrid_icon2;
	
	private String forth_date;
	private String forth_weather;
	//private String forth_temp;
	private String forth_feng;
	private int  forth_icon1;
	private int  forth_icon2;
	
	private int[] temphigh = new int[4];
	private int[] templow = new int [4];
	private int max_temp = -100;
	private int min_temp = 100;
	private String[] tempStrings= new String[4];
	
	private SQLiteDatabase db=null;
	private MyDataBaseHelper  helper =null;
	private String cityCode;
	private Context context;
	public PraseWeatherUtil(Context context,String CityCode){
		//this.details = WebServiceUtil.getWeatherByCity(CityCode);
		helper = new MyDataBaseHelper(context);
		this.cityCode = CityCode;
		this.context =context;
	}
	
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public boolean FileIfexist(){
		String dirPath="/data/data/com.example.mainsurface/databases";
	    File dir = new File(dirPath);
	    if(!dir.exists()) {
	        dir.mkdir();
	    }
		File file = new File(dir,"weather.db");
		if(!file.exists()) {
		    return false;
		}else{
			return true;
		}
	}
	public boolean getInf(){
		boolean flag =false;
		if(FileIfexist()){
			db = helper.getReadableDatabase();
			Cursor cursor = db.rawQuery("select * from Today where city_id ="+this.cityCode, null);
			if(cursor.moveToNext()){
				refresh();
				flag = true;
			}else{
				flag =false;
			}
		}else{
			flag = false;
		}
		return flag;
	}
	public void getRefresh(){
		WeatherInfToDB wDb = new WeatherInfToDB(this.context, this.cityCode);
		wDb.getInf();//从网络获取天气数据存入数据库
		refresh();//从数据库中获取天气数据
	}
	private void refresh(){
		db = helper.getReadableDatabase();
		/**
		 * 通过数据库获得当天天气状况
		 */
		Cursor cursor = db.rawQuery("select * from Today where city_id = "+this.cityCode, null);
		while(cursor.moveToNext()){
			date_time3 = cursor.getString(cursor
					.getColumnIndex("update_time"));
			first_qiwen5 = cursor.getString(cursor
					.getColumnIndex("temp"));
			first_fengxiang6 = cursor.getString(cursor
					.getColumnIndex("fengli"));
			first_shudu=cursor.getString(cursor
					.getColumnIndex("shidu"));
			first_kongqi = cursor.getString(cursor
					.getColumnIndex("air_q"));
			first_ziwai = cursor.getString(cursor
					.getColumnIndex("ziwai"));
		}
		cursor.close();
		/**
		 * 通过数据库weather.db获取生活指数
		 */
		Cursor indexCursor = db.rawQuery("select * from IndexTable where city_id="+this.cityCode, null);
		while(indexCursor.moveToNext()){
			chuanyi_index = indexCursor.getString(indexCursor
					.getColumnIndex("chuanyi"));
			guomin_index = indexCursor.getString(indexCursor
					.getColumnIndex("guomin"));
			yundong_index = indexCursor.getString(indexCursor
					.getColumnIndex("yundong"));
			xiche_index = indexCursor.getString(indexCursor
					.getColumnIndex("xiche"));
			liangshai_index =indexCursor.getString(indexCursor
					.getColumnIndex("liang"));
			lvyou_index = indexCursor.getString(indexCursor
					.getColumnIndex("lvyou"));
			lukuang_index =indexCursor.getString(indexCursor
					.getColumnIndex("lukuang"));
			shushidu_index =indexCursor.getString(indexCursor
					.getColumnIndex("shushi"));
			kongqi_index= indexCursor.getString(indexCursor
					.getColumnIndex("kongqi"));
			ziwai_index =indexCursor.getString(indexCursor
					.getColumnIndex("ziwai"));
		}
		indexCursor.close();
		/**
		 * 通过数据库weather.db获得未来四天天气情况
		 */
		Cursor futureCursor = db.rawQuery("select * from Future where city_id ="+this.cityCode, null);
		
		int colum = futureCursor.getColumnCount();
		String[][] weather_f = new String[4][colum-1];
		int index = 0;
		while(futureCursor.moveToNext()){
			for(int i=0;i<colum-1;i++){
				String col_name = futureCursor.getColumnName(i);
				weather_f[index][i] = futureCursor.getString(futureCursor.getColumnIndex(col_name));
			}
			index++;
		}
		futureCursor.close();
		db.close();
		first_date7 = weather_f[0][0];
		today_weather4 = weather_f[0][1];
		tempStrings[0]=weather_f[0][2];
		first_feng9=weather_f[0][3];
		first_icon110=parseIcon(weather_f[0][4]);
		first_icon211=parseIcon(weather_f[0][5]);
		/**
		 * 第二天
		 */
		second_date =weather_f[1][0];
		second_weather =weather_f[1][1];
		tempStrings[1] = weather_f[1][2];
		second_feng = weather_f[1][3];
		second_icon1 =parseIcon(weather_f[1][4]);
		second_icon2 =parseIcon(weather_f[1][5]);
		/**
		 * 第三天
		 */
		third_date = weather_f[2][0];
		thrid_weather =weather_f[2][1];
		tempStrings[2]=weather_f[2][2];
		thrid_feng=weather_f[2][3];
		thrid_icon1=parseIcon(weather_f[2][4]);
		thrid_icon2=parseIcon(weather_f[2][5]);
		/**
		 * 第四天
		 */
		forth_date =weather_f[3][0] ;
		forth_weather =weather_f[3][1];
		tempStrings[3] =weather_f[3][2];
		forth_feng =weather_f[3][3];
		forth_icon1 =parseIcon(weather_f[3][4]);
		forth_icon2 =parseIcon(weather_f[3][5]);
		
		
	
	}
	
	/**
	 * 得到成员变量的值和一些这些值组成的特殊类型数据。
	 */
	public void gettemprateInt(){
		
		for(int i=0;i<tempStrings.length;i++){
			String[] arrStrings=tempStrings[i].split("/");
			templow[i] = Integer.parseInt(arrStrings[0].split("℃")[0]);
			temphigh[i] = Integer.parseInt(arrStrings[1].split("℃")[0]);
			if(temphigh[i]>max_temp){
				max_temp = temphigh[i];
			}
			if(templow[i]<min_temp){
				min_temp = templow[i];
			}
		}
		
	}
	
	public int getMax_temp() {
		return max_temp;
	}
	public int getMin_temp() {
		return min_temp;
	}
	public String[] getTempStrings() {
		return tempStrings;
	}
	public int[] getTemphigh() {
		return temphigh;
	}
	public int[] getTemplow() {
		return templow;
	}
	public String[] getContent_index(){
		String[] str = {chuanyi_index,guomin_index,yundong_index,xiche_index,
				liangshai_index,lvyou_index,lukuang_index,shushidu_index,
				kongqi_index,ziwai_index};
		return str;
	}
	public String getDate_time3() {
		return date_time3;
	}
	public String getProvince0() {
		return province0;
	}
	public String getProvince_id2() {
		return province_id2;
	}
	public String getCity_text1() {
		return city_text1;
	}
	public String getToday_weather4() {
		return today_weather4;
	}
	public String getFirst_qiwen5() {
		return first_qiwen5;
	}
	public String getFirst_fengxiang6() {
		return first_fengxiang6;
	}

	public String getFirst_feng9() {
		return first_feng9;
	}
	public int getFirst_icon110() {
		return first_icon110;
	}
	public int getFirst_icon211() {
		return first_icon211;
	}
	public String getFirst_shudu() {
		return first_shudu;
	}
	public String getFirst_kongqi() {
		return first_kongqi;
	}
	public String getFirst_ziwai() {
		return first_ziwai;
	}
	public String getChuanyi_index() {
		return chuanyi_index;
	}
	public String getGuomin_index() {
		return guomin_index;
	}
	public String getYundong_index() {
		return yundong_index;
	}
	public String getXiche_index() {
		return xiche_index;
	}
	public String getLiangshai_index() {
		return liangshai_index;
	}
	public String getLvyou_index() {
		return lvyou_index;
	}
	public String getLukuang_index() {
		return lukuang_index;
	}
	public String getShushidu_index() {
		return shushidu_index;
	}
	public String getKongqi_index() {
		return kongqi_index;
	}
	public String getZiwai_index() {
		return ziwai_index;
	}
	
	public String getFirst_date7() {
		return first_date7;
	}
	public String getSecond_date() {
		return second_date;
	}
	public String getSecond_weather() {
		return second_weather;
	}

	public String getSecond_feng() {
		return second_feng;
	}
	public int getSecond_icon1() {
		return second_icon1;
	}
	public int getSecond_icon2() {
		return second_icon2;
	}
	public String getThird_date() {
		return third_date;
	}
	public String getThrid_weather() {
		return thrid_weather;
	}

	public String getThrid_feng() {
		return thrid_feng;
	}
	public int getThrid_icon1() {
		return thrid_icon1;
	}
	public int getThrid_icon2() {
		return thrid_icon2;
	}
	public String getForth_date() {
		return forth_date;
	}
	public String getForth_weather() {
		return forth_weather;
	}
	public String getForth_feng() {
		return forth_feng;
	}
	public int getForth_icon1() {
		return forth_icon1;
	}
	public int getForth_icon2() {
		return forth_icon2;
	}
	// 工具方法，该方法负责把返回的天气图标字符串，转换为程序的图片资源ID。
	private int parseIcon(String strIcon)
	{
		if (strIcon == null)
			return -1;
		if ("0.gif".equals(strIcon))
			return R.drawable.a_0;
		if ("1.gif".equals(strIcon))
			return R.drawable.a_1;
		if ("2.gif".equals(strIcon))
			return R.drawable.a_2;
		if ("3.gif".equals(strIcon))
			return R.drawable.a_3;
		if ("4.gif".equals(strIcon))
			return R.drawable.a_4;
		if ("5.gif".equals(strIcon))
			return R.drawable.a_5;
		if ("6.gif".equals(strIcon))
			return R.drawable.a_6;
		if ("7.gif".equals(strIcon))
			return R.drawable.a_7;
		if ("8.gif".equals(strIcon))
			return R.drawable.a_8;
		if ("9.gif".equals(strIcon))
			return R.drawable.a_9;
		if ("10.gif".equals(strIcon))
			return R.drawable.a_10;
		if ("11.gif".equals(strIcon))
			return R.drawable.a_11;
		if ("12.gif".equals(strIcon))
			return R.drawable.a_12;
		if ("13.gif".equals(strIcon))
			return R.drawable.a_13;
		if ("14.gif".equals(strIcon))
			return R.drawable.a_14;
		if ("15.gif".equals(strIcon))
			return R.drawable.a_15;
		if ("16.gif".equals(strIcon))
			return R.drawable.a_16;
		if ("17.gif".equals(strIcon))
			return R.drawable.a_17;
		if ("18.gif".equals(strIcon))
			return R.drawable.a_18;
		if ("19.gif".equals(strIcon))
			return R.drawable.a_19;
		if ("20.gif".equals(strIcon))
			return R.drawable.a_20;
		if ("21.gif".equals(strIcon))
			return R.drawable.a_21;
		if ("22.gif".equals(strIcon))
			return R.drawable.a_22;
		if ("23.gif".equals(strIcon))
			return R.drawable.a_23;
		if ("24.gif".equals(strIcon))
			return R.drawable.a_24;
		if ("25.gif".equals(strIcon))
			return R.drawable.a_25;
		if ("26.gif".equals(strIcon))
			return R.drawable.a_26;
		if ("27.gif".equals(strIcon))
			return R.drawable.a_27;
		if ("28.gif".equals(strIcon))
			return R.drawable.a_28;
		if ("29.gif".equals(strIcon))
			return R.drawable.a_29;
		if ("30.gif".equals(strIcon))
			return R.drawable.a_30;
		if ("31.gif".equals(strIcon))
			return R.drawable.a_31;
		return 0;
	}
	
}
