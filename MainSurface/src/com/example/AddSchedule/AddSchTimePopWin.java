package com.example.AddSchedule;

import java.util.Calendar;

import kankan.wheel.widget.ArrayWheelAdapter;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;

import com.example.calDrawView.CalendarVariable;
import com.example.mainsurface.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.PopupWindow;
import android.widget.TextView;

public class AddSchTimePopWin {
	private PopupWindow TimePopWin;
	private View popView;
	private Context context;
	private String[] monthStrings;
	private String[] daysStrings;
	private String[] hourStrings;
	private String[] minuteStrings;
	private Bundle bundle = null;

	private int curmonth = 0;
	private int curday = 0;
	private int curhour = 0;
	private int curminute = 0;

	private boolean if_allday = false;

	public AddSchTimePopWin(Context context, Bundle bundle) {
		this.context = context;
		popView = ((Activity) context).getLayoutInflater().inflate(
				R.layout.addsch_timer_popwin, null);
		/**
		 * 创建PopupWindow对象
		 */
		TimePopWin = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);// 高度为三分之一的屏幕高度
		/**
		 * 设置popwindow一些参数：背景，焦点，点击外部会自动消失
		 */
		TimePopWin.setBackgroundDrawable(context.getResources().getDrawable(
				R.color.schedule_add_popbg));
		TimePopWin.setFocusable(true);
		TimePopWin.setOutsideTouchable(true);
		setFinishBtn();
		/**
		 * 初始化一些数组参数
		 */
		monthStrings = getmonthString();
		daysStrings = getdaysString();
		hourStrings = getHourString();
		minuteStrings = getMinuteString();
		/**
		 * 初始化时间显示text
		 */
		this.bundle = bundle;
		inittime();
		InitTimes(if_allday);
		/**
		 * wheel初始化
		 */
		// InitWheelID();
		InitWheel(R.id.sch_time_month_wheel);
		InitWheel(R.id.sch_time_day_wheel);
		InitWheel(R.id.sch_time_hour_wheel);
		InitWheel(R.id.sch_time_minute_wheel);
		/**
		 * 全选点击响应
		 */
		InitCheckBox();
	}

	private void InitCheckBox() {
		CheckBox quantiBox = (CheckBox) popView
				.findViewById(R.id.pop_select_btn_allday);
		quantiBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					WheelView wheel1 = (WheelView) popView
							.findViewById(R.id.sch_time_hour_wheel);
					WheelView wheel2 = (WheelView) popView
							.findViewById(R.id.sch_time_minute_wheel);
					wheel1.setVisibility(View.GONE);
					wheel2.setVisibility(View.GONE);
					if_allday = true;
					InitTimes(if_allday);
				} else {
					WheelView wheel1 = (WheelView) popView
							.findViewById(R.id.sch_time_hour_wheel);
					WheelView wheel2 = (WheelView) popView
							.findViewById(R.id.sch_time_minute_wheel);
					wheel1.setVisibility(View.VISIBLE);
					wheel2.setVisibility(View.VISIBLE);
					if_allday = false;
					InitTimes(if_allday);
				}
			}
		});
	}

	private void inittime() {
		curmonth = bundle.getInt("month");
		curday = bundle.getInt("day");
		curhour = bundle.getInt("hour");
		curminute = bundle.getInt("minute");
	}

	private void InitTimes(boolean ifallday) {
		TextView tV = (TextView) ((Activity) context).findViewById(R.id.timeTx);
		String[] weeks = new String[] { "", "日", "一", "二", "三", "四", "五", "六" };
		Calendar cal = Calendar.getInstance();
		cal.set(CalendarVariable.getCurrentYear(), curmonth, curday);
		String time = "";
		if (ifallday) {
			time = monthStrings[curmonth] + daysStrings[curday - 1] + " 周"
					+ weeks[cal.get(Calendar.DAY_OF_WEEK)];
		} else {
			time = monthStrings[curmonth] + daysStrings[curday - 1] + " 周"
					+ weeks[cal.get(Calendar.DAY_OF_WEEK)] + " "
					+ String.format("%02d", curhour) + ":"
					+ String.format("%02d", curminute);
		}
		tV.setText(time);
	}

	private String[] getmonthString() {
		String[] month = new String[12];
		for (int i = 1; i <= 12; i++) {
			month[i - 1] = String.format("%02d", i) + "月";
		}
		return month;
	}

	private String[] getdaysString() {
		Calendar cal = Calendar.getInstance();
		int daysnum = getMonthDays(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH));
		String[] days = new String[daysnum];
		for (int i = 1; i <= daysnum; i++) {
			days[i - 1] = String.format("%02d", i) + "日";
		}
		return days;
	}

	private String[] getHourString() {
		String[] hours = new String[24];
		for (int i = 0; i < 24; i++)
			hours[i] = String.format("%02d", i) + "点";
		return hours;
	}

	private String[] getMinuteString() {
		String[] minute = new String[12];
		for (int i = 0; i < 12; i++)
			minute[i] = String.format("%02d", i * 5) + "分";
		return minute;
	}

	private void setFinishBtn() {
		Button finishtBtn = (Button) popView
				.findViewById(R.id.pop_select_btn_ok);
		finishtBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 判断PopupWindow是否还在显示
				if (TimePopWin.isShowing()) {
					// 关闭PopupWindow窗口
					TimePopWin.dismiss();
				}
			}
		});
	}

	public void openPopWindow(View view) {
		TimePopWin.showAtLocation(
				view,
				Gravity.BOTTOM, 0, 0);
	}

	// private void InitWheelID(){
	// MONTH_ID =R.id.sch_time_month_wheel;
	// DAYS_ID =R.id.sch_time_day_wheel;
	// HOURS_ID =R.id.sch_time_hour_wheel;
	// MINUTE_ID = R.id.sch_time_minute_wheel;
	//
	// }
	private void InitWheel(int id) {
		WheelView wheel = null;
		switch (id) {
		case R.id.sch_time_month_wheel:
			wheel = (WheelView) popView.findViewById(R.id.sch_time_month_wheel);
			wheel.setAdapter(new ArrayWheelAdapter<String>(monthStrings,
					monthStrings.length));
			wheel.setCurrentItem(curmonth);
			break;
		case R.id.sch_time_day_wheel:
			wheel = (WheelView) popView.findViewById(R.id.sch_time_day_wheel);
			wheel.setAdapter(new ArrayWheelAdapter<String>(daysStrings,
					daysStrings.length));
			wheel.setCurrentItem(curday - 1);
			break;
		case R.id.sch_time_hour_wheel:
			wheel = (WheelView) popView.findViewById(R.id.sch_time_hour_wheel);
			wheel.setAdapter(new ArrayWheelAdapter<String>(hourStrings,
					hourStrings.length));
			wheel.setCurrentItem(curhour);
			break;
		case R.id.sch_time_minute_wheel:
			wheel = (WheelView) popView
					.findViewById(R.id.sch_time_minute_wheel);
			wheel.setAdapter(new ArrayWheelAdapter<String>(minuteStrings,
					minuteStrings.length));
			wheel.setCurrentItem(curminute / 5);
			break;
		}
		wheel.setVisibleItems(3);
		wheel.addChangingListener(changedListener);
		wheel.addScrollingListener(scrollListener);
		wheel.setCyclic(true);
		wheel.setInterpolator(new AnticipateOvershootInterpolator());

	}

	private boolean wheelscrolled = false;
	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
		@Override
		public void onScrollingStarted(WheelView wheel) {
			wheelscrolled = true;

		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			wheelscrolled = false;

		}
	};
	OnWheelChangedListener changedListener = new OnWheelChangedListener() {
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			if (!wheelscrolled) {
				InitTimes(if_allday);
			} else {
				switch (wheel.getId()) {
				case R.id.sch_time_month_wheel:
					curmonth = newValue;
					break;
				case R.id.sch_time_day_wheel:
					curday = newValue + 1;
					break;
				case R.id.sch_time_hour_wheel:
					curhour = newValue;
					break;
				case R.id.sch_time_minute_wheel:
					curminute = newValue * 5;
					break;
				}
				InitTimes(if_allday);
			}
		}
	};

	private int getMonthDays(int year, int month) {
		month++;// 转换到实际月份
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12: {
			return 31;
		}
		case 4:
		case 6:
		case 9:
		case 11: {
			return 30;
		}
		case 2: {
			if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
				return 29;
			else
				return 28;
		}
		default:
			return 0;
		}
	}
}
