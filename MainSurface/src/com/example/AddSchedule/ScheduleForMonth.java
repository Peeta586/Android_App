package com.example.AddSchedule;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import com.example.EditSchedule.EditScheduleActivity;
import com.example.MainFragment.WanFragment.Month.TabFragmentWan;
import com.example.calDrawView.CalendarVariable;
import com.example.mainsurface.MainActivity;
import com.example.mainsurface.R;
import com.example.util.Utility;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

public class ScheduleForMonth {
	private Context context;
	private View view;
	private LinearLayout todayListShow;
	private TextView numberSch;
	private ListView  listView;
	private ScheduleListViewAdapter adapter;

	public ScheduleForMonth(Context context, View view) {
		this.context = context;
		this.view = view;
		InitKongjian();
		AddListViewforLinear(getListMap());
	}

	private void InitKongjian() {
		todayListShow = (LinearLayout) view.findViewById(R.id.sch_month_show);
		numberSch = (TextView) view.findViewById(R.id.month_today_numsch);
		//situation_sch = (TextView) view.findViewById(R.id.month_sch_situation);
	}
	private List<Map<String, Object>> getListMap (){
		Calendar cal = Calendar.getInstance();
		cal.set(CalendarVariable.getCurrentYear(),
				CalendarVariable.getCurrentMonth(),
				CalendarVariable.getTouchDay());
		Date d = new Date(cal.getTimeInMillis());
		ScheduleToList schlists = new ScheduleToList(context, d);
		List<Map<String, Object>> maps = schlists.setScheduleInMonth();
		return maps;
	}
	private void AddListViewforLinear(List<Map<String, Object>> maps) {
		listView = new ListView(context);
		if (maps != null) {
			adapter = new ScheduleListViewAdapter(maps, context);
			listView.setAdapter(adapter);
			listView.setDividerHeight(0);
			listView.setSelector(R.drawable.list_item_bg);
			Utility.setListViewHeightBasedOnChildren(listView);
			listView.setOnItemClickListener(new MyItemOnCLick());
			todayListShow.addView(listView);
			numberSch.setText("有" + listView.getCount() + "个日程");
		}
	}
	public void refresh(){
		adapter.refreshInfo(getListMap());
		adapter.notifyDataSetChanged();
		Utility.setListViewHeightBasedOnChildren(listView);
		numberSch.setText("有" + adapter.getCount() + "个日程");
	}
	class MyItemOnCLick implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			String idString = String.valueOf(id);
			Intent intent = new Intent();
			intent.setClass(context, EditScheduleActivity.class);
			intent.putExtra("id", idString);
			TabFragmentWan wan =(TabFragmentWan) ((MainActivity)context).getSupportFragmentManager().findFragmentByTag("wannian");
			wan.startActivityForResult(intent,
					MainActivity.getRequestCodeEditsch());
			Toast.makeText(context, idString, Toast.LENGTH_SHORT).show();
			
		}
		
	}
	
}
