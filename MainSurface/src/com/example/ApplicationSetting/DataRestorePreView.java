package com.example.ApplicationSetting;

import java.util.List;
import java.util.Map;

import com.example.DataBase.MyScheduleDBUtil;
import com.example.DataBase.MyTodoScheduleDB;
import com.example.ImportExport.JsonToSDtxt;
import com.example.mainsurface.R;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class DataRestorePreView extends Activity {
	private List<Map<String, Object>> list = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_data_restore_set);
		InitBtn();
		InitListView();
	}

	private void InitBtn() {
		// 返回
		Button back = (Button) findViewById(R.id.btn_backupAndRecovery_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// 恢复
		Button restorebtn = (Button) findViewById(R.id.restorebtn);
		restorebtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RestoreSch();
				Toast.makeText(getApplicationContext(), "o亲!您数据恢复成功!",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void InitListView() {
		ListView listView = (ListView) findViewById(R.id.lv_backUpData);
		JsonToSDtxt jsonToS = new JsonToSDtxt(this);
		list = jsonToS.GetJsonFromSD();
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.detail_data_restore_list_item, new String[] {
						"startDate", "startTime", "name" }, new int[] {
						R.id.tv_startDate, R.id.tv_startTime,
						R.id.preview_sch_name });
		listView.setAdapter(adapter);
	}

	private void RestoreSch() {
		MyTodoScheduleDB helper = new MyTodoScheduleDB(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		for (int i = 0; i < list.size(); i++) {
			String endDate = (String) list.get(i).get("endDate");
			String endTime = (String) list.get(i).get("endTime");
			Cursor cursor = db
					.rawQuery(
							"select * from Schedule where endDate =? and endTime =?",
							new String[] { endDate, endTime });
			if(cursor.getCount() == 0){
				ResortInsert(i);
			}
		}
		if(db !=null)
			db.close();
	}
	private void ResortInsert(int i){
		int remind = Integer.parseInt(String.valueOf(list.get(i).get("remind")));
		int repeat_type = Integer.parseInt(String.valueOf(list.get(i).get("repeat_type")));
		/**
		 * values的构造和一封装到一个函数中
		 */
		ContentValues values = new ContentValues();
		values.put("startDate", String.valueOf(list.get(i).get("startDate")));
		values.put("startTime", String.valueOf(list.get(i).get("startTime")));
		values.put("endDate", String.valueOf(list.get(i).get("endDate")));
		values.put("endTime", String.valueOf(list.get(i).get("endTime")));
		values.put("isfullday", Boolean.getBoolean(String.valueOf(list.get(i).get("isfullday"))));
		values.put("status", Integer.parseInt(String.valueOf(list.get(i).get("status"))));
		values.put("remind", Integer.parseInt(String.valueOf(list.get(i).get("remind"))));
		values.put("name", String.valueOf(list.get(i).get("name")));
		values.put("repeat_day", Integer.parseInt(String.valueOf(list.get(i).get("repeat_day"))));
		values.put("repeat_week", Integer.parseInt(String.valueOf(list.get(i).get("repeat_week"))));
		values.put("repeat_month", Integer.parseInt(String.valueOf(list.get(i).get("repeat_month"))));
		values.put("repeat_type", Integer.parseInt(String.valueOf(list.get(i).get("repeat_type"))));
		values.put("repeat_number", Integer.parseInt(String.valueOf(list.get(i).get("repeat_number"))));
		/**
		 * 添加到日程列表中
		 */
		new MyScheduleDBUtil(this).Insert(values, remind, repeat_type);
	}

}
