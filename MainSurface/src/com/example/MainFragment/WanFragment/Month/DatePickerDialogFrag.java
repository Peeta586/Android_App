package com.example.MainFragment.WanFragment.Month;

import com.example.MainFragment.TabFragmentHuang;
import com.example.calDrawView.CalendarTools;
import com.example.mainsurface.R;
import com.example.mainsurface.R.style;

import kankan.wheel.widget.ArrayWheelAdapter;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class DatePickerDialogFrag extends DialogFragment {
	private View view;
	private CalendarTools calTools = new CalendarTools();

	// private TabFragmentWan tfw ;//= new TabFragmentWan();
	private Button button_ok;
	private Button button_cancel;
	/**
	 * 公农历选择
	 */
	private RadioButton radiogong;
	private RadioButton radionong;

	private String[] yearStrings = new String[200];
	private String[] monthStrings = new String[12];
	// 选择的radio是农历还是公历
	private boolean GONG_NONG = true;
	public static int YEARWHEEL_ID = 0;
	public static int MONTHWHEEL_ID = 1;
	public static int DAYWHEEL_ID = 2;
	// ///用于暂存当前年月日
	public int curYear = 1900;
	public int curMonth = 1;
	public int curDay = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setStyle(STYLE_NO_FRAME, style.DialogStyle);
		monthStrings = this.getActivity().getResources()
				.getStringArray(R.array.month);
		for (int i = 0; i < 200; i++) {
			yearStrings[i] = (1900 + i) + "年";
		}
		/**
		 * 先初始化本类的临时变量。curYear，curMonth，curDay
		 */
		if (DatePickerDialogFrag.this.getTag().equals("date_huangli")) {
			curYear = TabFragmentHuang.getHuangliYear();
			curMonth = TabFragmentHuang.getHuangliMonth();
			curDay = TabFragmentHuang.getHuangliDay();
		} else if(DatePickerDialogFrag.this.getTag().equals("datepicker")) {
			curYear = calTools.getCurrentYear();
			curMonth = calTools.getCurrentMonth();
			curDay = calTools.getTouchDay();
		}else{//生理周期
			
		}
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.datepicker_dialog, container, false);
		/**
		 * 初始化年月日卷轴
		 */
		initWheel(R.id.year);
		initWheel(R.id.month);
		initWheel(R.id.day);
		updateStatus();

		/**
		 * 确定按钮
		 */
		button_ok = (Button) view.findViewById(R.id.button_ok);
		button_ok.setOnClickListener(new MybtnOnClick());
		/**
		 * 回到今天按钮
		 */
		button_cancel = (Button) view.findViewById(R.id.button_cancel);
		button_cancel.setText("回到今天");
		button_cancel.setOnClickListener(new MybtnOnClick());
		/*
		 * 公农历的选择
		 */
		radiogong = (RadioButton) view.findViewById(R.id.radio0);
		radionong = (RadioButton) view.findViewById(R.id.radio1);
		if (GONG_NONG) {
			radiogong.setChecked(true);
		} else {
			radionong.setChecked(true);
		}
		radiogong.setOnClickListener(new MyradioOnClick());
		radionong.setOnClickListener(new MyradioOnClick());

		return view;
	}

	/**
	 * 更新黄历的title_gre_ym
	 */
	private void UpdateHuangliShow() {
		((TabFragmentHuang) DatePickerDialogFrag.this.getTargetFragment())
				.refresh();
	}

	private void SetDateTimeDialog() {
		((TabFragmentWan) DatePickerDialogFrag.this.getTargetFragment())
				.SetDateTimeFortitle(TabFragmentWan.getCURRENT_INDEX());
	}

	/**
	 * 点击监听类
	 */
	class MybtnOnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button_ok:
				// 判断当下视图为黄历视图还是万年历视图
				if (DatePickerDialogFrag.this.getTag().equals("date_huangli")) {//黄历
					TabFragmentHuang.setHuangliYear(curYear);
					TabFragmentHuang.setHuangliMonth(curMonth);
					TabFragmentHuang.setHuangliDay(curDay);
					UpdateHuangliShow();

				} else if(DatePickerDialogFrag.this.getTag().equals("datepicker")){//万年历
					calTools.setCurrentYear(curYear);
					calTools.setCurrentMonth(curMonth);
					calTools.SetTouchDay(curDay);
					RefreshAllCalView();
					SetDateTimeDialog();// 更新title中日期视图
				}else{//生理周期
					
				}

				break;
			case R.id.button_cancel:
				// 判断当下视图为黄历视图还是万年历视图
				if (DatePickerDialogFrag.this.getTag().equals("date_huangli")){//黄历
					TabFragmentHuang.setHuangliYear(calTools.getCurentYearR());
					TabFragmentHuang.setHuangliMonth(calTools
							.getCurrentMonthR());
					TabFragmentHuang.setHuangliDay(calTools.getCurrentDay());
					UpdateHuangliShow();
				} else if(DatePickerDialogFrag.this.getTag().equals("datepicker")){//万年历
					calTools.SetReallyTime();
					RefreshAllCalView();
					SetDateTimeDialog();

				}else {
					
				}
				break;
			}
			DatePickerDialogFrag.this.dismiss();
		}
	}

	/**
	 * 根据当前显示的视图类型，更新其他视图
	 */
	private void RefreshAllCalView() {
		switch (TabFragmentWan.getCURRENT_INDEX()) {
		case 0:// 当前周视图，需要更新周和月视图
			((TabFragmentWan) DatePickerDialogFrag.this.getTargetFragment())
					.getmFragment().addCard.refreshCalView();
			((TabFragmentWan) DatePickerDialogFrag.this.getTargetFragment())
					.getwFragment().refresh();
			break;
		case 1:// 当前月视图，需要更新周和月和日三个视图
			((TabFragmentWan) DatePickerDialogFrag.this.getTargetFragment())
					.getmFragment().addCard.refreshCalView();
			((TabFragmentWan) DatePickerDialogFrag.this.getTargetFragment())
					.getwFragment().refresh();
			((TabFragmentWan) DatePickerDialogFrag.this.getTargetFragment())
					.getdFragment().refresh();
			break;
		case 2:// 当前日视图，需要更新日和月视图
			((TabFragmentWan) DatePickerDialogFrag.this.getTargetFragment())
					.getmFragment().addCard.refreshCalView();
			((TabFragmentWan) DatePickerDialogFrag.this.getTargetFragment())
					.getdFragment().refresh();
			break;
		}
	}

	class MyradioOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.radio0:
				GONG_NONG = true;
				initWheel(R.id.month);
				initWheel(R.id.day);
				break;
			case R.id.radio1:
				GONG_NONG = false;
				initWheel(R.id.month);
				initWheel(R.id.day);
				break;
			}
		}

	}

	// Wheel scrolled flag
	private boolean wheelScrolled = false;

	// Wheel scrolled listener
	OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
		public void onScrollingStarted(WheelView wheel) {
			wheelScrolled = true;
		}

		public void onScrollingFinished(WheelView wheel) {
			wheelScrolled = false;
			updateStatus();
		}
	};
	/**
	 * 解决区分农历和阳历
	 */
	// Wheel changed listener
	private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			// 开始的时候会运行if里面的东西
			if (!wheelScrolled) {
				updateStatus();

			} else {
				if (wheel.getId() == DatePickerDialogFrag.YEARWHEEL_ID) {// 年转轴
					// calTools.setCurrentYear(1900+newValue);
					curYear = 1900 + newValue;
					initWheel(R.id.month);
					initWheel(R.id.day);
					updateStatus();
				} else if (wheel.getId() == DatePickerDialogFrag.MONTHWHEEL_ID) {// 月转轴
					// calTools.setCurrentMonth(newValue);
					curMonth = newValue;
					initWheel(R.id.day);
					updateStatus();
				} else if (wheel.getId() == DatePickerDialogFrag.DAYWHEEL_ID) {// 日转轴
					// calTools.SetTouchDay(newValue);
					curDay = newValue + 1;
					updateStatus();
				}
			}
			Log.i("monthnum", String.valueOf(oldValue));
			Log.i("monthnum", String.valueOf(newValue));
		}
	};

	/**
	 * Updates entered PIN status
	 */
	private void updateStatus() {
		int year = curYear;
		int month = curMonth;
		int day = curDay;
		TextView text = (TextView) view
				.findViewById(R.id.tv_datepickerdialog_bottomTime);
		String string = year + "年" + String.format("%02d", month + 1) + "月"
				+ String.format("%02d", day) + "日 ["
				+ calTools.GetCurrentWeekOfYear(curYear, curMonth, curDay)
				+ "周]周" + calTools.GetDayWeek(curYear, curMonth, curDay);
		text.setText(string);
		/*
		 * if (testPin(2, 4, 6, 1)) { text.setText("Congratulation!"); } else {
		 * text.setText("Invalid PIN"); }
		 */
	}

	/**
	 * Initializes wheel
	 *
	 * @param id
	 *            the wheel widget Id
	 */
	private void initWheel(int id) {
		WheelView wheel;
		switch (id) {
		case R.id.year:
			wheel = getWheel(id);
			YEARWHEEL_ID = wheel.getId();
			wheel.setAdapter(new ArrayWheelAdapter<String>(yearStrings,
					yearStrings.length));
			wheel.setCurrentItem(curYear - 1900);
			wheel.setVisibleItems(5);
			wheel.addChangingListener(changedListener);
			wheel.addScrollingListener(scrolledListener);
			wheel.setCyclic(false);
			wheel.setInterpolator(new AnticipateOvershootInterpolator());
			break;
		case R.id.month:
			wheel = getWheel(id);
			MONTHWHEEL_ID = wheel.getId();
			if (GONG_NONG) {
				wheel.setAdapter(new ArrayWheelAdapter<String>(monthStrings,
						monthStrings.length));
				wheel.setCurrentItem(curMonth);
			} else {
				wheel.setAdapter(new ArrayWheelAdapter<String>(calTools
						.getMonthsOfLunar(curYear), calTools
						.getMonthsOfLunar(curYear).length));
				wheel.setCurrentItem(calTools.getCurrentLunarMonth() - 1);// curLunarMonth
			}
			wheel.addChangingListener(changedListener);
			wheel.addScrollingListener(scrolledListener);
			wheel.setCyclic(true);
			wheel.setInterpolator(new AnticipateOvershootInterpolator());
			break;
		case R.id.day:
			wheel = getWheel(id);
			DAYWHEEL_ID = wheel.getId();
			if (GONG_NONG) {
				wheel.setAdapter(new ArrayWheelAdapter<String>(calTools
						.getdayStrings(curYear, curMonth), calTools
						.getdayStrings(curYear, curMonth).length));
				wheel.setCurrentItem(curDay - 1);
			} else {
				wheel.setAdapter(new ArrayWheelAdapter<String>(
						calTools.getdayslunarsStrings(curYear, curMonth, curDay),
						calTools.getdayslunarsStrings(curYear, curMonth, curDay).length));
				wheel.setCurrentItem(curDay - 1);// 改为农历的日期
			}
			wheel.addChangingListener(changedListener);
			wheel.addScrollingListener(scrolledListener);
			wheel.setCyclic(true);
			wheel.setInterpolator(new AnticipateOvershootInterpolator());

			break;
		}

	}

	/**
	 * Returns wheel by Id通過id返回whellView
	 * 
	 * @param id
	 *            the wheel Id
	 * @return the wheel with passed Id
	 */
	private WheelView getWheel(int id) {
		return (WheelView) view.findViewById(id);
	}

	/**
	 * Tests entered PIN
	 * 
	 * @param v1
	 * @param v2
	 * @param v3
	 * @param v4
	 * @return true
	 */
	/*
	 * private boolean testPin(int v1, int v2, int v3, int v4) { return
	 * testWheelValue(R.id.passw_1, v1) && testWheelValue(R.id.passw_2, v2) &&
	 * testWheelValue(R.id.passw_3, v3) && testWheelValue(R.id.passw_4, v4); }
	 */

	/**
	 * Tests wheel value
	 * 
	 * @param id
	 *            the wheel Id
	 * @param value
	 *            the value to test
	 * @return true if wheel value is equal to passed value
	 */
	// private boolean testWheelValue(int id, int value) {
	// return getWheel(id).getCurrentItem() == value;
	// }

	/**
	 * @param id
	 * @return,返回年
	 */
	// private int YearWheelValue(int id) {
	// return getWheel(id).getCurrentItem() + 1900;
	// }

	/**
	 * Mixes wheel
	 * 
	 * @param id
	 *            the wheel id
	 * 
	 *            private void mixWheel(int id) { WheelView wheel =
	 *            getWheel(id); wheel.scroll(-25 + (int)(Math.random() * 50),
	 *            2000); }
	 */
}
