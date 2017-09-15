package com.example.AddSchedule;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import com.example.mainsurface.R;

import android.content.Context;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SchduleForWeek {
	private Context context;
	private View view;
	private LinearLayout SunLayout;
	private LinearLayout MonLayout;
	private LinearLayout TueLayout;
	private LinearLayout WedLayout;
	private LinearLayout ThuLayout;
	private LinearLayout FriLayout;
	private LinearLayout SatLayout;
	private Date date;
	private final static int WEEK_DAYS = 7;
	//private final static int DAYS_NUM = 5;
	private List<LinearLayout> listlayouts =new ArrayList<LinearLayout>();
	private List<TextView> listTextViews = new ArrayList<TextView>();
	public SchduleForWeek(Context context, Date date, View view) {
		this.context = context;
		this.view = view;
		this.date = date;// 为本周第一天的日期
		// helper =new MyTodoScheduleDB(context);
		InitLinears();
	}

	private void InitLinears() {
		// 初始化
		SunLayout = (LinearLayout) view.findViewById(R.id.Sun_schdule_show);
		MonLayout = (LinearLayout) view.findViewById(R.id.Mon_schdule_show);
		TueLayout = (LinearLayout) view.findViewById(R.id.Tues_schdule_show);
		WedLayout = (LinearLayout) view.findViewById(R.id.Wednes_schdule_show);
		ThuLayout = (LinearLayout) view.findViewById(R.id.Thurs_schdule_show);
		FriLayout = (LinearLayout) view.findViewById(R.id.Fri_schdule_show);
		SatLayout = (LinearLayout) view.findViewById(R.id.Satur_schdule_show);
		// 先清除子view
		SunLayout.removeAllViews();
		MonLayout.removeAllViews();
		TueLayout.removeAllViews();
		WedLayout.removeAllViews();
		ThuLayout.removeAllViews();
		FriLayout.removeAllViews();
		SatLayout.removeAllViews();
		//
		listlayouts.add(0, SunLayout);
		listlayouts.add(1, MonLayout);
		listlayouts.add(2, TueLayout);
		listlayouts.add(3, WedLayout);
		listlayouts.add(4, ThuLayout);
		listlayouts.add(5, FriLayout);
		listlayouts.add(6, SatLayout);
	
		listTextViews.add(0,(TextView)view.findViewById(R.id.Sun_numsch));
		listTextViews.add(1,(TextView)view.findViewById(R.id.Mon_numsch));
		listTextViews.add(2,(TextView)view.findViewById(R.id.Tues_numsch));
		listTextViews.add(3,(TextView)view.findViewById(R.id.Wednes_numsch));
		listTextViews.add(4,(TextView)view.findViewById(R.id.Thurs_numsch));
		listTextViews.add(5,(TextView)view.findViewById(R.id.Fri_numsch));
		listTextViews.add(6,(TextView)view.findViewById(R.id.Satur_numsch));
	}

	public  void ScheduleTolist() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		for (int i = 0; i < WEEK_DAYS; i++) {
			if(i==0)//表示第一天
				cal.add(Calendar.DATE, 0);
			else
				cal.add(Calendar.DATE, 1);
			Date d = new Date(cal.getTimeInMillis());
			ScheduleToList schlists = new ScheduleToList(context, d);
			List<Map<String, Object>> maps = schlists.setScheduleInWeek();
			if(maps !=null){
				ListView listView = new ListView(context);
				SimpleAdapter adapter = new SimpleAdapter(context, maps,
						R.layout.sche_week_list_item, new String[] { "startTime",
								"name" }, new int[] { R.id.week_stime_item_list,
								R.id.week_sname_item_list });
				listView.setAdapter(adapter);
				listView.setDividerHeight(0);
				listView.setSelected(false);
				listTextViews.get(i).setText("有"+listView.getCount()+"个日程");
				listTextViews.get(i).setVisibility(View.VISIBLE);
				listlayouts.get(i).addView(listView);
			}else{
				listTextViews.get(i).setVisibility(View.GONE);
			}
		}
	}
}
