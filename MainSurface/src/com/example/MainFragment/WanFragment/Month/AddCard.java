package com.example.MainFragment.WanFragment.Month;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.calDrawView.CalendarTools;
import com.example.mainsurface.R;

public class AddCard {
	private Context context;	
	private LinearLayout mainLayout =null;
	private MonthGridViewAdapter adapter=null;
	private CalendarTools caTools =new CalendarTools();
	
	public AddCard(Context context,View view) {
		this.context =context;
		mainLayout =(LinearLayout)view.findViewById(R.id.month_main_linear);
	}
	
	public void GetFirstView() {
		/**
		 * 先将日历view添加到布局文件中，然后再讲布局文件添加到mainlayout中
		 */
		RelativeLayout mRLayout = null;
		mRLayout = (RelativeLayout) mainLayout.findViewById(R.id.calendar_Rel);
		LayoutInflater inflater =LayoutInflater.from(context);
		LinearLayout layout =(LinearLayout)inflater.inflate(R.layout.calendar_month_linear2, null);
		GridView monthGridView =(GridView)layout.findViewById(R.id.mymonthgridview);
		adapter = new MonthGridViewAdapter(context, caTools.caList());
		monthGridView.setAdapter(adapter);
		monthGridView.setOnItemClickListener(new MyGridItemClickListener(context));
		/**
		 * 添加日历view
		 */
		mRLayout.addView(layout);
	}
	public void AddView(View addview){
		mainLayout.addView(addview);
	}
	public LinearLayout getMainLayout() {
		return mainLayout;
	}
	public void refreshCalView(){
		adapter.setList(caTools.caList());
		adapter.notifyDataSetChanged();
	}
	
}
