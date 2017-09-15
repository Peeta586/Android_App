package com.example.MainFragment;

import java.util.Calendar;

import com.example.MainFragment.WanFragment.Month.DatePickerDialogFrag;

import com.example.calDrawView.CalendarVariable;
import com.example.mainsurface.R;
import com.example.util.JYUtil;
import com.example.util.Lunar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TabFragmentHuang extends Fragment {
	private View view;
	private Button share;

	private RelativeLayout dateTimeLayout;
	private ImageView img_nian;
	private ImageView img_niantext;
	private ImageView lunar1;
	private ImageView lunar2;
	private ImageView lunar3;
	private ImageView lunar4;

	private TextView titile_hl_ym;
	private TextView gtime_week;
	private TextView tv_weeknum;
	private TextView ganzhiTime;
	private TextView description;
	private TextView festival;
	private TextView tx_yi;
	private TextView tx_ji;

	private static int huangliYear = CalendarVariable.getCurrentYear();
	private static int huangliMonth = CalendarVariable.getCurrentMonth();
	private static int huangliDay = CalendarVariable.getTouchDay();
	private Calendar calendar = Calendar.getInstance();
	private Lunar lunar = new Lunar();

	private int[] imgMonth;
	private int[] imgDayshi;
	private int[] imgDayge;

	// private String weekString;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		calendar.set(huangliYear, huangliMonth, huangliDay);
		lunar.setDate(calendar.getTime());
		imgMonth = new int[] { R.drawable.nl_zheng, R.drawable.nl_two,
				R.drawable.nl_three, R.drawable.nl_four, R.drawable.nl_five,
				R.drawable.nl_six, R.drawable.nl_seven, R.drawable.nl_eight,
				R.drawable.nl_nine, R.drawable.nl_ten, R.drawable.nl_dong,
				R.drawable.nl_la };
		imgDayge = new int[] { R.drawable.nl_ten, R.drawable.nl_one,
				R.drawable.nl_two, R.drawable.nl_three, R.drawable.nl_four,
				R.drawable.nl_five, R.drawable.nl_six, R.drawable.nl_seven,
				R.drawable.nl_eight, R.drawable.nl_nine };
		imgDayshi = new int[] { R.drawable.nl_chu, R.drawable.nl_ten,
				R.drawable.nl_nian, R.drawable.nl_three };
		super.onCreate(savedInstanceState);
	}

	public static int getHuangliYear() {
		return huangliYear;
	}

	public static void setHuangliYear(int huangliYear) {
		TabFragmentHuang.huangliYear = huangliYear;
	}

	public static int getHuangliMonth() {
		return huangliMonth;
	}

	public static void setHuangliMonth(int huangliMonth) {
		TabFragmentHuang.huangliMonth = huangliMonth;
	}

	public static int getHuangliDay() {
		return huangliDay;
	}

	public static void setHuangliDay(int huangliDay) {
		TabFragmentHuang.huangliDay = huangliDay;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.huangli_activity, container, false);
		Init();// 初始化所有控件
		setTitle_hl_ym();
		setNianImg();
		setHuangTime();
		setFestival();
		setJYInf();

		// 设置日期布局点击事件
		dateTimeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatePickerDialogFrag dialogFrag = new DatePickerDialogFrag();
				// dialogFrag.setTargetFragment(mFragment, 0);
				dialogFrag.setTargetFragment(TabFragmentHuang.this, 0);
				dialogFrag.show(getActivity().getSupportFragmentManager(),
						"date_huangli");
			}
		});
		/**
		 * 分享
		 */
		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		return view;
	}

	private void Init() {
		img_nian = (ImageView) view.findViewById(R.id.Img_nian);
		img_niantext = (ImageView) view.findViewById(R.id.Img_niantext);
		lunar1 = (ImageView) view.findViewById(R.id.lunar1);
		lunar2 = (ImageView) view.findViewById(R.id.lunar2);
		lunar3 = (ImageView) view.findViewById(R.id.lunar3);
		lunar4 = (ImageView) view.findViewById(R.id.lunar4);
		titile_hl_ym = (TextView) view.findViewById(R.id.huangli_titile_hl_ym);
		gtime_week = (TextView) view.findViewById(R.id.gtime_week);
		tv_weeknum = (TextView) view.findViewById(R.id.tv_weeknum);
		ganzhiTime = (TextView) view.findViewById(R.id.ntime);
		description = (TextView) view.findViewById(R.id.tv_huangli_decri);
		festival = (TextView) view.findViewById(R.id.tv_huangli_festival);
		tx_yi = (TextView) view.findViewById(R.id.tx_yi);
		tx_ji = (TextView) view.findViewById(R.id.tx_ji);
		dateTimeLayout = (RelativeLayout) view
				.findViewById(R.id.huang_DateTimeInfo);
		share = (Button) view.findViewById(R.id.share);
	}

	private void setFestival() {
		calendar.set(huangliYear, huangliMonth, huangliDay);
		lunar.setDate(calendar.getTime());
		description.setText(lunar.getDescription());
		if (lunar.isFestival()) {
			if (lunar.isLFestival() && lunar.isSFestival()){
				festival.setText("节日：" + lunar.getLFestivalName() + "|"
						+ lunar.getSFestivalName());
				festival.setVisibility(View.VISIBLE);
			}
			else{
				if(lunar.isLFestival()){
					festival.setText("节日：" + lunar.getLFestivalName());
					festival.setVisibility(View.VISIBLE);
				}else if(lunar.isSFestival()){
					festival.setText("节日：" + lunar.getSFestivalName());
					festival.setVisibility(View.VISIBLE);
				}else{
					festival.setVisibility(View.GONE);
				}
			}
		}
	}

	private void setJYInf() {
		JYUtil jyUtil = new JYUtil();
		jyUtil.calendar(huangliYear, huangliMonth, huangliDay - 1);
		if (jyUtil.getJy().equals("")) {
			tx_ji.setText(jyUtil.getJ());
			tx_yi.setText(jyUtil.getY());
		} else {
			tx_ji.setText(jyUtil.getJy());
			tx_yi.setText(jyUtil.getJy());
		}
	}

	private void setTitle_hl_ym() {
		String str1 = huangliYear + "."
				+ String.format("%02d", huangliMonth + 1) + "."
				+ String.format("%02d", huangliDay);
		titile_hl_ym.setText(str1);
	}

	private void setNianImg() {
		calendar.set(huangliYear, huangliMonth, huangliDay);
		lunar.setDate(calendar.getTime());
		if ("鼠".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a01);
			img_niantext.setImageResource(R.drawable.b01);
		} else if ("牛".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a02);
			img_niantext.setImageResource(R.drawable.b02);

		} else if ("虎".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a03);
			img_niantext.setImageResource(R.drawable.b03);

		} else if ("兔".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a04);
			img_niantext.setImageResource(R.drawable.b04);

		} else if ("龙".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a05);
			img_niantext.setImageResource(R.drawable.b05);

		} else if ("蛇".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a06);
			img_niantext.setImageResource(R.drawable.b06);

		} else if ("马".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a07);
			img_niantext.setImageResource(R.drawable.b07);

		} else if ("羊".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a08);
			img_niantext.setImageResource(R.drawable.b08);

		} else if ("猴".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a09);
			img_niantext.setImageResource(R.drawable.b09);

		} else if ("鸡".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a10);
			img_niantext.setImageResource(R.drawable.b10);

		} else if ("狗".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a11);
			img_niantext.setImageResource(R.drawable.b11);

		} else if ("猪".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a12);
			img_niantext.setImageResource(R.drawable.b12);

		}
	}

	/**
	 * @return设置今天是星期几
	 */
	private String loadOthers() {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日");
		String weekString = "星期";
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			weekString += "日";
			break;
		case 2:
			weekString += "一";
			break;
		case 3:
			weekString += "二";
			break;
		case 4:
			weekString += "三";
			break;
		case 5:
			weekString += "四";
			break;
		case 6:
			weekString += "五";
			break;
		case 7:
			weekString += "六";
			break;
		}
		return weekString;
	}

	/**
	 * @return设置这是本年的第几周
	 */
	private String setWeekOfYear() {
		String weekofyear = "第";
		weekofyear += String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
		weekofyear += "周";
		return weekofyear;
	}

	private String setGanZhiTime() {
		String ganzhi = lunar.getCyclicaYear() + " (" + lunar.getAnimalString()
				+ ")年  ";
		ganzhi += lunar.getCyclicaMonth() + "月  ";
		ganzhi += lunar.getCyclicaDay() + "日";
		return ganzhi;
	}

	private void setImgLunar() {
		lunar2.setImageResource(R.drawable.nl_yue);
		lunar1.setImageResource(imgMonth[lunar.getLunarMonth() - 1]);
		if (lunar.getLunarDay() == 10) {
			lunar3.setImageResource(imgDayshi[0]);
		} else {
			lunar3.setImageResource(imgDayshi[lunar.getLunarDay() / 10]);
		}
		lunar4.setImageResource(imgDayge[lunar.getLunarDay() % 10]);
	}

	private void setHuangTime() {
		// 需要重新设定事件，用于刷新
		calendar.set(huangliYear, huangliMonth, huangliDay);
		lunar.setDate(calendar.getTime());
		// 设置今天是星期几
		gtime_week.setText(loadOthers());
		// 设置第几周
		tv_weeknum.setText(setWeekOfYear());
		ganzhiTime.setText(setGanZhiTime());
		setImgLunar();
	}

	public void refresh() {
		setTitle_hl_ym();
		setNianImg();
		setHuangTime();
		setFestival();
		setJYInf();
	}

}
