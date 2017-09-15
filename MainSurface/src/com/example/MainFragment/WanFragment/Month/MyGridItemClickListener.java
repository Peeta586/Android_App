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
			 * ���ݵ����cell��������
			 */
			caltool.SetTouchDay(position-first+2);
			caltool.SetTouchTime(CalendarVariable.currentYear,
					CalendarVariable.currentMonth);
			/**
			 * ����������ͼ
			 */
			RefreshViews();
		}
	}

	/**
	 * ͬ����������ͼ������ͼ������Title�е�����
	 */
	private void RefreshViews() {
		// �ҵ�TabFragmentWan����
		TabFragmentWan wan = (TabFragmentWan) ((MainActivity) context)
				.getSupportFragmentManager().findFragmentByTag("wannian");
		// ����Title�е�����
		wan.SetDateTimeFortitle(TabFragmentWan.getCURRENT_INDEX());
		// ��������ͼ
		wan.getwFragment().refresh();
		// ��������ͼ
		wan.getdFragment().refresh();
		wan.getmFragment().refresh1();
	}
}
