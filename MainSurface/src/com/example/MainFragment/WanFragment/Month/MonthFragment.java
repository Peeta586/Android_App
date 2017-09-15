package com.example.MainFragment.WanFragment.Month;

import com.example.AddSchedule.ScheduleForMonth;
import com.example.calDrawView.CalendarTools;
import com.example.mainsurface.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MonthFragment extends Fragment {
	private View view;
	private Button preMonth;
	private Button nextMonth;
	private Button nowMonth;
	protected AddCard addCard=null;
	private CalendarTools calTools = new CalendarTools();
	private ScheduleForMonth schmonth =null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("monthfrag","oncreate");
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.calview_month_list, container,false);
		 Log.i("monthF","oncreateview");
		 addCard =new AddCard(getActivity(),view);
		 addCard.GetFirstView();
		 //addCard.AddView();
		 /**
		  * 设置按钮事件
		  */
		 SetBtnOnClick();
		 schmonth = new ScheduleForMonth(getActivity(), view);
		return view;
	}
	
	private void SetBtnOnClick(){
		preMonth = (Button)view.findViewById(R.id.pre_month_btn_1);
		nextMonth = (Button)view.findViewById(R.id.next_month_btn);
		nowMonth =(Button)view.findViewById(R.id.now_month_btn);
		preMonth.setOnClickListener(new MyBtnOnClick());
		nextMonth.setOnClickListener(new MyBtnOnClick());
		nowMonth.setOnClickListener(new MyBtnOnClick());
	}
	class MyBtnOnClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.pre_month_btn_1:
				calTools.SetPreviousMonth();
				refresh();
				((TabFragmentWan)getParentFragment()).SetDateTimeFortitle(TabFragmentWan.getCURRENT_INDEX());
				refreshOtherView();
				break;
			case R.id.next_month_btn:
				calTools.SetNextMonth();
				refresh();
				((TabFragmentWan)getParentFragment()).SetDateTimeFortitle(TabFragmentWan.getCURRENT_INDEX());
				refreshOtherView();
				break;
			case R.id.now_month_btn:
				calTools.SetReallyTime();
				refresh();
				((TabFragmentWan)getParentFragment()).SetDateTimeFortitle(TabFragmentWan.getCURRENT_INDEX());
				refreshOtherView();
				break;
			}
		}	
	}
	/**
	 * 同步刷新日视图和周视图
	 */
	private void refreshOtherView(){
		((TabFragmentWan)getParentFragment()).getwFragment().refresh();
		((TabFragmentWan)getParentFragment()).getdFragment().refresh();
	}
	public void refresh(){
		addCard.refreshCalView();
		schmonth.refresh();
	}
	public void refresh1(){
		schmonth.refresh();
	}

}
