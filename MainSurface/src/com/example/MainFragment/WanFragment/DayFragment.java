package com.example.MainFragment.WanFragment;

import java.sql.Date;
import java.util.Calendar;

import com.example.AddSchedule.AddSchActivity;
import com.example.AddSchedule.ScheduleListView;
import com.example.MainFragment.WanFragment.Month.TabFragmentWan;
import com.example.calDrawView.CalendarTools;
import com.example.mainsurface.MainActivity;
import com.example.mainsurface.R;
import com.example.util.JYUtil;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DayFragment extends Fragment {
	private View view;
	private Button preday;
	private Button today;
	private Button nextday;
	private CalendarTools calTools = new CalendarTools();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i("dayF", "oncreateview");
		view = inflater.inflate(R.layout.calview_day_list, container, false);
		setJYInf();
		setDateTimeForNT();
		setSchedule();
		/**
		 * ��������ճ̼�������
		 */
		setLinearSchOnClick();
		/**
		 * ���ð�ť����������ǰһ�죬���죬��һ�죩
		 */
		setBtnOnclick();
		return view;
	}

	/**
	 * ���button�����¼�
	 */
	private void setBtnOnclick() {
		preday = (Button) view.findViewById(R.id.pre_day_btn);
		today = (Button) view.findViewById(R.id.today_day_btn);
		nextday = (Button) view.findViewById(R.id.next_day_btn);
		preday.setOnClickListener(new MyBtnOnClick());
		today.setOnClickListener(new MyBtnOnClick());
		nextday.setOnClickListener(new MyBtnOnClick());

	}

	/**
	 * @author LSHM ͬ��ˢ������ͼ
	 */
	class MyBtnOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.pre_day_btn:// ǰһ��
				calTools.SetPreDay();
				((TabFragmentWan) getParentFragment())
						.SetDateTimeFortitle(TabFragmentWan.getCURRENT_INDEX());
				refresh();// ˢ������ͼ
				refreshMonthView();
				break;
			case R.id.today_day_btn:// ����
				calTools.SetReallyTime();
				((TabFragmentWan) getParentFragment())
						.SetDateTimeFortitle(TabFragmentWan.getCURRENT_INDEX());
				refresh();// ˢ������ͼ
				refreshMonthView();
				break;
			case R.id.next_day_btn:// ��һ��
				calTools.SetNextDay();
				((TabFragmentWan) getParentFragment())
						.SetDateTimeFortitle(TabFragmentWan.getCURRENT_INDEX());
				refresh();// ˢ������ͼ
				refreshMonthView();
				break;
			}

		}

	}

	private void refreshMonthView() {
		TabFragmentWan wan = (TabFragmentWan) this.getParentFragment();
		wan.getmFragment().refresh();
	}

	/**
	 * �˼���Ϣ
	 */
	private void setJYInf() {
		JYUtil jyUtil = new JYUtil();
		jyUtil.calendar(calTools.getCurrentYear(), calTools.getCurrentMonth(),
				calTools.getTouchDay() - 1);
		TextView tx_yi = (TextView) view.findViewById(R.id.tv_nongli_yi);
		TextView tx_ji = (TextView) view.findViewById(R.id.tv_nongli_ji);
		if (jyUtil.getJy().equals("")) {
			tx_ji.setText(jyUtil.getJ());
			tx_yi.setText(jyUtil.getY());
		} else {
			tx_ji.setText(jyUtil.getJy());
			tx_yi.setText(jyUtil.getJy());
		}
	}

	/**
	 * ����������Ϣ
	 */
	private void setDateTimeForNT() {
		TextView tView = (TextView) view.findViewById(R.id.tv_nongli_date);
		TextView tView2 = (TextView) view.findViewById(R.id.tv_nongli_month);
		tView.setText("��" + calTools.GetDayWeek());
		tView2.setText(calTools.getTianGanMonth());
	}

	/**
	 * ��������ճ�linearlayout�ĵ���¼�
	 */
	private void setLinearSchOnClick() {
		LinearLayout layout = (LinearLayout) view
				.findViewById(R.id.quan_sched_linear);
		layout.setOnClickListener(new MyLinearOnClick());
		layout = (LinearLayout) view.findViewById(R.id.lingchen_sched_linear);
		layout.setOnClickListener(new MyLinearOnClick());
		layout = (LinearLayout) view.findViewById(R.id.shangwu_sched_linear);
		layout.setOnClickListener(new MyLinearOnClick());
		layout = (LinearLayout) view.findViewById(R.id.xianwu_sched_linear);
		layout.setOnClickListener(new MyLinearOnClick());
		layout = (LinearLayout) view.findViewById(R.id.night_sched_linear);
		layout.setOnClickListener(new MyLinearOnClick());

	}

	class MyLinearOnClick implements OnClickListener {
		// ����ճ���Ӧ��
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getActivity(), AddSchActivity.class);
			switch (v.getId()) {
			case R.id.quan_sched_linear:
				intent.putExtras(getTimeBundle());
				getParentFragment().startActivityForResult(intent,
						MainActivity.getRequestCodeAddsch());
				break;
			case R.id.lingchen_sched_linear:
				intent.putExtras(getTimeBundle());
				getParentFragment().startActivityForResult(intent,
						MainActivity.getRequestCodeAddsch());
				break;
			case R.id.shangwu_sched_linear:
				intent.putExtras(getTimeBundle());
				getParentFragment().startActivityForResult(intent,
						MainActivity.getRequestCodeAddsch());
				break;
			case R.id.xianwu_sched_linear:
				intent.putExtras(getTimeBundle());
				getParentFragment().startActivityForResult(intent,
						MainActivity.getRequestCodeAddsch());
				break;
			case R.id.night_sched_linear:
				intent.putExtras(getTimeBundle());
				getParentFragment().startActivityForResult(intent,
						MainActivity.getRequestCodeAddsch());
				break;
			}
		}
	}

	private Bundle getTimeBundle() {
		Bundle bundle = new Bundle();
		bundle.putInt("month", calTools.getCurrentMonth());
		bundle.putInt("day", calTools.getTouchDay());
		bundle.putInt("hour", calTools.getHour());
		bundle.putInt("minute", calTools.getMinute());
		return bundle;
	}

	public void refresh() {
		setJYInf();
		setDateTimeForNT();
		setSchedule();
	}

	public void setSchedule() {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(calTools.getCurrentYear(),
				calTools.getCurrentMonth(),
				calTools.getTouchDay());
		Date date = new java.sql.Date(cal.getTimeInMillis());
		ScheduleListView sListView =new ScheduleListView(getActivity(), date, view);
		sListView.addlistForLinears();
	}
	
}
