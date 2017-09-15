package com.example.Weather;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.Weather.City.CityActivity;
import com.example.mainsurface.MainActivity;
import com.example.mainsurface.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.ListView;
import android.widget.RadioButton;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TabFragmentTian extends Fragment {
	private View view;
	private View view1, view2, view3;
	private ViewPager viewPager;
	// private RadioGroup radioGroup;
	private RadioButton radio1_jin;
	private RadioButton radio2_wei;
	private RadioButton radio3_sheng;

	private final static int RAD_TODAY = 0;
	private final static int RAD_FUTURE = 1;
	private final static int RAD_LIFE = 2;
	private static int CURRENT_INDEX;
	private final int duration = Toast.LENGTH_LONG;
	private List<View> views;

	// protected SoapObject details;
	protected ProgressDialog progressDialog = null;
	protected PraseWeatherUtil p;

	protected Calendar calendar = Calendar.getInstance();
	protected int CurrentWeek;
	private static String cityCode = "1944";
	private static String city_name = "�Ͼ�";

	//private static final int REQUEST_CODE = 2;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// progressDialog.dismiss();
			switch (msg.what) {
			case 1:
				progressDialog.dismiss();// ���Ի���ر�
				InitCityName(city_name);
				refreshViews();
				break;
			case -1:
				progressDialog.dismiss();// ���Ի���ر�
				Toast.makeText(
						TabFragmentTian.this.getActivity()
								.getApplicationContext(), "��ȡ����ʧ�ܣ�", duration)
						.show();
				break;
			}
			// super.handleMessage(msg);
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i("weather", "onCreate");
		super.onCreate(savedInstanceState);
		p = new PraseWeatherUtil(getActivity(), cityCode);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.weather_activity, container, false);
		// ��ʼ���ؼ�
		InitTitle();
		InitRadioBtn();
		InitViewPager();
		/**
		 * ������ͼ
		 */
		// �ڶ�������Ϊfalse����ʾ����ˢ�£�ֻ�Ǵ����ݿ��ж�ȡ���ݡ�
		
		p.setCityCode(TabFragmentTian.cityCode);
		if (p.getInf()) {// ������˵�����ݿ�����
			refreshViews();
		}
		// ��ʼ���ڶ���viewPagerҳ�����ƿ�ͼ

		InitCityName(TabFragmentTian.city_name);// ��ʼ����������
		Log.i("weather", "onCreateVIew");
		return view;
	}
	private void refreshViews() {
		setView1();
		setView2();
		setView3();
	}

	private void InitCityName(String cityName) {
		TextView tView = (TextView) view.findViewById(R.id.textView_city);
		tView.setText(cityName);
	}

	private void InitTitle() {
		// ���ó��а�ť������
		TextView chengtView = (TextView) view.findViewById(R.id.textView_city);
		chengtView.setOnClickListener(new MyTitleOnClick());
		// ���ø��ºͷ���ť������
		Button btn_share = (Button) view.findViewById(R.id.btn_share);
		Button btn_refresh = (Button) view.findViewById(R.id.btn_refresh);
		btn_refresh.setOnClickListener(new MyTitleOnClick());
		btn_share.setOnClickListener(new MyTitleOnClick());
	}

	class MyTitleOnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.textView_city:
				Intent intent = new Intent();
				intent.setClass(getActivity(), CityActivity.class);
				startActivityForResult(intent, MainActivity.getRequestCodeWeather());
				break;
			case R.id.btn_refresh:
				getWeather();
				break;
			case R.id.btn_share:
				break;
			}

		}
	}

	private void getWeather() {
		progressDialog = new ProgressDialog(TabFragmentTian.this.getActivity());
		progressDialog.setMessage("���ڻ�ȡ����...");
		progressDialog.show();
		new Thread(new GetWeatherTask(cityCode)).start();
	}

	/**
	 * @author LSHM:�¼��߳�����ˢ��������Ϣ
	 *         �̴߳����ݿ�weather.db�ж�ȡ��Ϣ��������ݿ�Ϊ�գ��ʹ����������Ϊ��������Ҳ��Ϊ�գ��Ͳ�ѯ��ʾ
	 */
	private class GetWeatherTask implements Runnable {
		private String theCityCode;

		// private boolean if_refresh;
		public GetWeatherTask(String theCityCode) {
			super();
			this.theCityCode = theCityCode;
			// this.if_refresh = if_refresh;
			Log.i("weather", "Thread");
		}

		@Override
		public void run() {
			try {
				
				p.setCityCode(theCityCode);
				p.getRefresh();
				Log.i("weather", "Thread_true");
				// InitCityName(city_name);//�����ٷ�UI�߳��з���UI�߳�

				handler.obtainMessage(1).sendToTarget();
			} catch (Exception e) {
				if (p.getInf()) {
					handler.obtainMessage(1).sendToTarget();
				} else {
					e.printStackTrace();
					handler.obtainMessage(-1).sendToTarget();
				}
			}
		}

	}

	public static void setCity_name(String city_name) {
		TabFragmentTian.city_name = city_name;
	}

	public static void setCityCode(String cityCode) {
		TabFragmentTian.cityCode = cityCode;
	}

	private void setView1() {
		TextView tView = (TextView) view1.findViewById(R.id.tv_nowtemperature);
		tView.setText(p.getFirst_qiwen5());
		tView = (TextView) view1.findViewById(R.id.tv_fengli_d);
		tView.setText(p.getFirst_fengxiang6());
		tView = (TextView) view1.findViewById(R.id.tv_refreshtime);
		tView.setText("����ʱ��:" + p.getDate_time3());
		// tView = (TextView)view1.findViewById(R.id.);
		/**
		 * ���ý��������仯ͼ���˵��
		 */
		ImageView img_w_icon = (ImageView) view1
				.findViewById(R.id.today_w_icon2);
		img_w_icon.setImageResource(p.getFirst_icon211());
		img_w_icon = (ImageView) view1.findViewById(R.id.today_w_icon1);
		img_w_icon.setImageResource(p.getFirst_icon110());
		tView = (TextView) view1.findViewById(R.id.today_weather_qing);
		tView.setText(p.getToday_weather4());
		/**
		 * �����¶ȣ�ʪ�ȣ�����/��������Ϣ
		 */
		tView = (TextView) view1.findViewById(R.id.tv_hightodaytemperature);
		tView.setText(p.getTempStrings()[0]);
		tView = (TextView) view1.findViewById(R.id.tv_fl);
		tView.setText(p.getFirst_feng9());
		tView.requestFocus();
		tView = (TextView) view1.findViewById(R.id.tv_shidu);
		tView.setText(p.getFirst_shudu());
		tView = (TextView) view1.findViewById(R.id.tv_ziwaiqing);
		tView.setText(p.getFirst_ziwai());
		tView = (TextView) view1.findViewById(R.id.tv_kongqi);
		tView.setText(p.getFirst_kongqi());
		/**
		 * ����δ����������
		 */
		tView =(TextView)view1.findViewById(R.id.first_fengli);
		tView.setText(p.getSecond_feng());
		tView =(TextView)view1.findViewById(R.id.second_fengli);
		tView.setText(p.getThrid_feng());
		tView =(TextView)view1.findViewById(R.id.third_fengli);
		tView.setText(p.getForth_feng());
	}

	private void setView2() {
		RelativeLayout rlLayout = (RelativeLayout) view2
				.findViewById(R.id.lineartemp_view);
		TemperatureView tempview = new TemperatureView(this.getActivity(), p);
		rlLayout.removeAllViews();
		rlLayout.addView(tempview, 0);

		/**
		 * ��������ͼ�������
		 */
		TextView tView = (TextView) view2.findViewById(R.id.tv_tomo_date);
		tView.setText("��" + getWeekName(1));
		tView = (TextView) view2.findViewById(R.id.tv_after_date);
		tView.setText("��" + getWeekName(2));
		tView = (TextView) view2.findViewById(R.id.tv_forth_date);
		tView.setText("��" + getWeekName(3));
		/**
		 * �������ڵİ���ͺ�ҹ���
		 */
		setView2_Img();
		setView2State();
	}

	private void setView2_Img() {
		/**
		 * ���ð��������ͼ��
		 */
		ImageView imgView = (ImageView) view2.findViewById(R.id.img_today_m);
		imgView.setImageResource(p.getFirst_icon110());
		ImageView imgView2 = (ImageView) view2.findViewById(R.id.img_today_n);
		imgView2.setImageResource(p.getFirst_icon211());

		imgView = (ImageView) view2.findViewById(R.id.img_tomo_m);
		imgView.setImageResource(p.getSecond_icon1());
		imgView2 = (ImageView) view2.findViewById(R.id.img_tomo_n);
		imgView2.setImageResource(p.getSecond_icon2());

		imgView = (ImageView) view2.findViewById(R.id.img_after_m);
		imgView.setImageResource(p.getThrid_icon1());
		imgView2 = (ImageView) view2.findViewById(R.id.img_after_n);
		imgView2.setImageResource(p.getThrid_icon2());

		imgView = (ImageView) view2.findViewById(R.id.img_forth_m);
		imgView.setImageResource(p.getForth_icon1());
		imgView2 = (ImageView) view2.findViewById(R.id.img_forth_n);
		imgView2.setImageResource(p.getForth_icon2());

	}

	private void setView2State() {
		TextView tView = (TextView) view2.findViewById(R.id.tv_weather_tod);
		TextView tView2 = (TextView) view2.findViewById(R.id.tv_today_n);
		String[] strs = getWeatherQ(p.getToday_weather4());
		tView.setText(strs[0]+"(��)");
		tView2.setText(strs[1]+"(ҹ)");
		tView = (TextView) view2.findViewById(R.id.tv_weather_tomo);
		tView2 = (TextView) view2.findViewById(R.id.tv_tomo_n);
		strs = getWeatherQ(p.getSecond_weather());
		tView.setText(strs[0]+"(��)");
		tView2.setText(strs[1]+"(ҹ)");
		tView = (TextView) view2.findViewById(R.id.tv_weather_after);
		tView2 = (TextView) view2.findViewById(R.id.tv_after_n);
		strs = getWeatherQ(p.getThrid_weather());
		tView.setText(strs[0]+"(��)");
		tView2.setText(strs[1]+"(ҹ)");
		tView = (TextView) view2.findViewById(R.id.tv_weather_forth);
		tView2 = (TextView) view2.findViewById(R.id.tv_forth_n);
		strs = getWeatherQ(p.getForth_weather());
		tView.setText(strs[0]+"(��)");
		tView2.setText(strs[1]+"(ҹ)");

	}

	/**
	 * ��������ͼ˵��
	 * 
	 * @param name
	 * @return:���ص��������仯��˵���������ת��ת�����򷵻�0�����ƣ�1������Ӧ����ͼ�꣺icon1��icon2
	 */
	private String[] getWeatherQ(String name) {
		String[] str = new String[2];
		String[] arrStrings = name.split("ת");
		str[0] = arrStrings[0];
		str[1] = arrStrings[arrStrings.length - 1];
		return str;
	}

	private String getWeekName(int i) {
		String[] array = p.getFirst_date7().split("��");
		int month= Integer.parseInt(array[0])-1;
		int day= Integer.parseInt(array[1].split("��")[0]);
		calendar.set(calendar.get(Calendar.YEAR), month, day);
		CurrentWeek =calendar.get(Calendar.DAY_OF_WEEK);
		String[] weekname = { "", "��", "һ", "��", "��", "��", "��", "��" };
		if (CurrentWeek + i <= 7) {
			return weekname[CurrentWeek + i];
		} else {
			return weekname[(CurrentWeek + i) % 8 + 1];
		}
	}

	private void setView3() {
		ListView listView = (ListView) view3.findViewById(R.id.index_listView);
		listView.setAdapter(new ZhiShuListViewAdapter(this.getActivity(), p
				.getContent_index()));
	}

	private void InitViewPager() {
		viewPager = (ViewPager) view.findViewById(R.id.pager);
		views = new ArrayList<View>();
		LayoutInflater inflater = this.getActivity().getLayoutInflater();
		view1 = inflater.inflate(R.layout.weather_current_view, null);
		view2 = inflater.inflate(R.layout.wether_tempture, null);
		view3 = inflater.inflate(R.layout.weather_zhishu, null);

		views.add(view1);
		views.add(view2);
		views.add(view3);
		viewPager.setAdapter(new MyViewPagerAdapter(views));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPagerChangerListener());
	}

	private void InitRadioBtn() {
		radio1_jin = (RadioButton) view.findViewById(R.id.jinri_btn);
		radio2_wei = (RadioButton) view.findViewById(R.id.qushi_btn);
		radio3_sheng = (RadioButton) view.findViewById(R.id.shenghuo_btn);

		radio1_jin.setOnClickListener(new MyOnClickListner(RAD_TODAY));
		radio2_wei.setOnClickListener(new MyOnClickListner(RAD_FUTURE));
		radio3_sheng.setOnClickListener(new MyOnClickListner(RAD_LIFE));
	}

	class MyOnClickListner implements OnClickListener {
		private int index = 0;

		public MyOnClickListner(int i) {
			this.index = i;
		}

		@Override
		public void onClick(View v) {
			viewPager.setCurrentItem(index);

		}
	}

	public class MyViewPagerAdapter extends PagerAdapter {
		private List<View> mListViews;

		public MyViewPagerAdapter(List<View> mList) {
			this.mListViews = mList;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mListViews.get(position));
			// super.destroyItem(container, position, object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mListViews.get(position));
			return mListViews.get(position);
		}

		@Override
		public int getCount() {
			return mListViews == null ? 0 : mListViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}

	public class MyOnPagerChangerListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case RAD_TODAY:
				setSelectedRadio(RAD_TODAY);
				CURRENT_INDEX = RAD_TODAY;
				break;
			case RAD_FUTURE:
				setSelectedRadio(RAD_FUTURE);
				CURRENT_INDEX = RAD_FUTURE;
				break;
			case RAD_LIFE:
				setSelectedRadio(RAD_LIFE);
				CURRENT_INDEX = RAD_LIFE;
				break;
			}

		}

	}

	private void setSelectedRadio(int id) {
		switch (CURRENT_INDEX) {
		case RAD_TODAY:
			radio1_jin.setChecked(false);
			radio1_jin.setSelected(false);
			break;
		case RAD_FUTURE:
			radio2_wei.setChecked(false);
			radio2_wei.setSelected(false);
			break;
		case RAD_LIFE:
			radio3_sheng.setChecked(false);
			radio3_sheng.setSelected(false);
			break;
		}
		switch (id) {
		case RAD_TODAY:
			radio1_jin.setChecked(true);
			radio1_jin.setSelected(true);
			break;
		case RAD_FUTURE:
			radio2_wei.setChecked(true);
			radio2_wei.setSelected(true);
			break;
		case RAD_LIFE:
			radio3_sheng.setChecked(true);
			radio3_sheng.setSelected(true);
			break;
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		views.clear();
		Log.i("weather","onDestroy()");
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == MainActivity.getRequestCodeWeather()) {
			if (resultCode == Activity.RESULT_OK) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					String city_code = extras.getString("city_code");
					String city_name = extras.getString("city_name");
					if (city_code != null && city_name != null) {
						setCity_name(city_name);
						setCityCode(city_code);
					}
				}
				getWeather();
				InitCityName(TabFragmentTian.city_name);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
		
	}

}
