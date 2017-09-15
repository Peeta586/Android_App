package com.example.MainFragment.WanFragment.Month;

import com.example.calDrawView.CalendarTools;
import com.example.calDrawView.CalendarVariable;
import com.example.mainsurface.MainActivity;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class MyGridItemClickListener implements OnItemClickListener,OnItemSelectedListener {
	private Context context;
	private CalendarTools caltool =new CalendarTools();
	public MyGridItemClickListener(Context context) {
		this.context =context;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//view.setSelected(true);
		int first = caltool.GetFirstDayOfWeek();
		if(position >=first-1){
			/**
			 * 根据点击的cell设置日期
			 */
			caltool.SetTouchDay(position-first+2);
			caltool.SetTouchTime(CalendarVariable.currentYear,
					CalendarVariable.currentMonth);
			/**
			 * 更新其他视图
			 */
			RefreshViews();
		}
	}

	/**
	 * 同步更新周视图和日视图，还有Title中的日期
	 */
	private void RefreshViews() {
		// 找到TabFragmentWan对象
		TabFragmentWan wan = (TabFragmentWan) ((MainActivity) context)
				.getSupportFragmentManager().findFragmentByTag("wannian");
		// 更新Title中的日期
		wan.SetDateTimeFortitle(TabFragmentWan.getCURRENT_INDEX());
		// 更新周视图
		wan.getwFragment().refresh();
		// 更新日视图
		wan.getdFragment().refresh();
		wan.getmFragment().refresh1();
	}
}
