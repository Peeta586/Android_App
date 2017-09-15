package com.example.AddSchedule;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.EditSchedule.EditScheduleActivity;
import com.example.MainFragment.WanFragment.Month.TabFragmentWan;
import com.example.mainsurface.MainActivity;
import com.example.mainsurface.R;
import com.example.util.Utility;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class ScheduleListView {
	private Context context;
	private LinearLayout qtLayout;
	private LinearLayout lcLayout;
	private LinearLayout swLayout;
	private LinearLayout xwLayout;
	private LinearLayout ngLayout;
	private Date date;
	private final static int DAY_SCHNUM =5;
	private List<List<Map<String, Object>>> lists=new ArrayList< List<Map<String,Object>>>();
	private List<LinearLayout> listlinear= new ArrayList<LinearLayout>();
	private View view;
	public ScheduleListView(Context context,Date date,View view) {
		this.context = context;
		this.date =date;
		this.view =view;
		IniteLinear();
		ScheduleInLinear();
	}
	private void IniteLinear(){
		//LayoutInflater inflater = LayoutInflater.from(context);
		//View view  = inflater.inflate(R.layout.calview_day_list, null);
		qtLayout =(LinearLayout)view.findViewById(R.id.quan_sched_linear1);
		lcLayout =(LinearLayout)view.findViewById(R.id.lingchen_sched_linear1);
		swLayout =(LinearLayout)view.findViewById(R.id.shangwu_sched_linear1);
		xwLayout =(LinearLayout)view.findViewById(R.id.xianwu_sched_linear1);
		ngLayout =(LinearLayout)view.findViewById(R.id.night_sched_linear1);
		qtLayout.removeAllViews();
		lcLayout.removeAllViews();
		swLayout.removeAllViews();
		xwLayout.removeAllViews();
		ngLayout.removeAllViews();
		listlinear.add(0,qtLayout);
		listlinear.add(1,lcLayout);
		listlinear.add(2,swLayout);
		listlinear.add(3,xwLayout);
		listlinear.add(4,ngLayout);
	}
	private void ScheduleInLinear(){
		ScheduleToList scheduleToList = new ScheduleToList(context, date);
		lists = scheduleToList.setScheduleInLinear();
	}
	public void addlistForLinears(){
		for(int i=0;i<DAY_SCHNUM;i++){
			if(lists.get(i) != null){
				Log.i("sche","-->lists"+i);
				//LayoutInflater inflater = LayoutInflater.from(context);
				//ListView listView =(ListView)inflater.inflate(R.layout.myday_sche_list, null);
				ListView listView =new ListView(context);
				ScheduleListViewAdapter adapter =new ScheduleListViewAdapter(lists.get(i),context);
				listView.setAdapter(adapter);
				listView.setDividerHeight(0);
				listView.setSelector(R.drawable.list_item_bg);
				Utility.setListViewHeightBasedOnChildren(listView);
				listView.setOnItemClickListener(new MyItemOnCLick());
				//listlinear.get(i).removeAllViews();
				listlinear.get(i).addView(listView);
			}
		}
		//添加完以后将lists清零
		///if(lists != null)
		lists.clear();
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
