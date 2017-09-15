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
		Init();// ��ʼ�����пؼ�
		setTitle_hl_ym();
		setNianImg();
		setHuangTime();
		setFestival();
		setJYInf();

		// �������ڲ��ֵ���¼�
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
		 * ����
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
				festival.setText("���գ�" + lunar.getLFestivalName() + "|"
						+ lunar.getSFestivalName());
				festival.setVisibility(View.VISIBLE);
			}
			else{
				if(lunar.isLFestival()){
					festival.setText("���գ�" + lunar.getLFestivalName());
					festival.setVisibility(View.VISIBLE);
				}else if(lunar.isSFestival()){
					festival.setText("���գ�" + lunar.getSFestivalName());
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
		if ("��".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a01);
			img_niantext.setImageResource(R.drawable.b01);
		} else if ("ţ".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a02);
			img_niantext.setImageResource(R.drawable.b02);

		} else if ("��".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a03);
			img_niantext.setImageResource(R.drawable.b03);

		} else if ("��".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a04);
			img_niantext.setImageResource(R.drawable.b04);

		} else if ("��".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a05);
			img_niantext.setImageResource(R.drawable.b05);

		} else if ("��".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a06);
			img_niantext.setImageResource(R.drawable.b06);

		} else if ("��".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a07);
			img_niantext.setImageResource(R.drawable.b07);

		} else if ("��".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a08);
			img_niantext.setImageResource(R.drawable.b08);

		} else if ("��".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a09);
			img_niantext.setImageResource(R.drawable.b09);

		} else if ("��".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a10);
			img_niantext.setImageResource(R.drawable.b10);

		} else if ("��".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a11);
			img_niantext.setImageResource(R.drawable.b11);

		} else if ("��".equals(lunar.getAnimalString())) {
			img_nian.setImageResource(R.drawable.a12);
			img_niantext.setImageResource(R.drawable.b12);

		}
	}

	/**
	 * @return���ý��������ڼ�
	 */
	private String loadOthers() {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy��M��d��");
		String weekString = "����";
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			weekString += "��";
			break;
		case 2:
			weekString += "һ";
			break;
		case 3:
			weekString += "��";
			break;
		case 4:
			weekString += "��";
			break;
		case 5:
			weekString += "��";
			break;
		case 6:
			weekString += "��";
			break;
		case 7:
			weekString += "��";
			break;
		}
		return weekString;
	}

	/**
	 * @return�������Ǳ���ĵڼ���
	 */
	private String setWeekOfYear() {
		String weekofyear = "��";
		weekofyear += String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
		weekofyear += "��";
		return weekofyear;
	}

	private String setGanZhiTime() {
		String ganzhi = lunar.getCyclicaYear() + " (" + lunar.getAnimalString()
				+ ")��  ";
		ganzhi += lunar.getCyclicaMonth() + "��  ";
		ganzhi += lunar.getCyclicaDay() + "��";
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
		// ��Ҫ�����趨�¼�������ˢ��
		calendar.set(huangliYear, huangliMonth, huangliDay);
		lunar.setDate(calendar.getTime());
		// ���ý��������ڼ�
		gtime_week.setText(loadOthers());
		// ���õڼ���
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
