package com.example.Weather;

import org.ksoap2.serialization.SoapObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.DataBase.MyDataBaseHelper;

/**
 * @author LSHM 这个类是从网络中获得天气数据，并存入到数据库weather.db中。
 */
public class WeatherInfToDB {
	private SoapObject details;
	private MyDataBaseHelper helper = null;
	private SQLiteDatabase db = null;
	private String cityCode;

	public WeatherInfToDB(Context context, String cityCode) {
		this.cityCode = cityCode;
		// details中已经获得了数据
		this.details = WebServiceUtil.getWeatherByCity(cityCode);
		helper = new MyDataBaseHelper(context);
	}

	public void getInf() {
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from Today where city_id="
				+ this.cityCode, null);
		if (cursor.moveToNext() && this.details != null
				&& !this.details.getProperty(1).equals("anyType{}")) {
			/**
			 * 先删除然后在添加
			 */
			db.delete("Today", "city_id = ?", new String[] { this.cityCode });
			db.delete("Future", "city_id = ?", new String[] { this.cityCode });
			db.delete("IndexTable", "city_id = ?",
					new String[] { this.cityCode });

			refresh();
		} else {
			refresh();
		}
		cursor.close();
	}

	private void refresh() {
		// db = helper.getWritableDatabase();
		/**
		 * 先添加未来4天天气预报表
		 */
		for (int i = 0; i < 4; i++) {
			int num = i * 5;
			ContentValues values = new ContentValues();
			String data_tw = details.getProperty(7 + num).toString();
			values.put("data_time", data_tw.split(" ")[0]);
			values.put("weather", data_tw.split(" ")[1]);
			values.put("temp", details.getProperty(8 + num).toString());
			values.put("fengli", details.getProperty(9 + num).toString());
			values.put("icon1", details.getProperty(10 + num).toString());
			values.put("icon2", details.getProperty(11 + num).toString());
			values.put("city_id", this.cityCode);
			db.insert("Future", null, values);
		}
		/**
		 * 今天天气状况
		 */
		ContentValues values = new ContentValues();
		values.put("update_time", details.getProperty(3).toString());
		String data_tw = details.getProperty(4).toString();
		String[] data_array = data_tw.split("：");
		values.put("temp", (data_array[2]).split("；")[0]);
		values.put("fengli", (data_array[3]).split("；")[0]);
		values.put("shidu", data_array[4]);
		data_tw = details.getProperty(5).toString();
		data_array = data_tw.split("：");
		values.put("air_q", data_array[1].split("；")[0]);
		values.put("ziwai", data_array[2]);
		values.put("city_id", this.cityCode);
		db.insert("Today", null, values);
		/**
		 * 添加生活指数表
		 */
		ContentValues indexValues = new ContentValues();
		data_tw = details.getProperty(6).toString();
		data_array = data_tw.split("。");
		indexValues.put("chuanyi", data_array[0].split("：")[1]);
		indexValues.put("guomin", data_array[1].split("：")[1]);
		indexValues.put("yundong", data_array[2].split("：")[1]);
		indexValues.put("xiche", data_array[3].split("：")[1]);
		indexValues.put("liang", data_array[4].split("：")[1]);
		indexValues.put("lvyou", data_array[5].split("：")[1]);
		indexValues.put("lukuang", data_array[6].split("：")[1]);
		indexValues.put("shushi", data_array[7].split("：")[1]);
		indexValues.put("kongqi", data_array[8].split("：")[1]);
		indexValues.put("ziwai", data_array[9].split("：")[1]);
		indexValues.put("city_id", this.cityCode);
		db.insert("IndexTable", null, indexValues);

		db.close();

	}

}
