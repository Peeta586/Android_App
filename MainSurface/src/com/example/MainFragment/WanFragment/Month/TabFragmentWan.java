package com.example.MainFragment.WanFragment.Month;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.LoginRegiste.LoginRegisteActivity;
import com.example.MainFragment.WanFragment.DayFragment;
import com.example.MainFragment.WanFragment.WeekFragment;
import com.example.calDrawView.CalendarTools;
import com.example.mainsurface.MainActivity;
import com.example.mainsurface.R;

/**
 * @author LSHM
 * 
 */
public class TabFragmentWan extends Fragment {

	private View view;// 总的view布局
	private ViewPager viewPager;
	private ImageView imageView1, imageView2, imageView3;// 周月日指示图标

	private static CalendarTools calTools = new CalendarTools();
	/**
	 * 定义页面item值
	 */
	private final static int IMG_WEEK = 0;
	private final static int IMG_MONTH = 1;
	private final static int IMG_DAY = 2;
	private static int CURRENT_INDEX;
	/**
	 * 页面list
	 */
	private List<Fragment> fragmentlist = null;

	private MonthFragment mFragment = null;
	private WeekFragment wFragment =null;
	private DayFragment dFragment =null;

	//private static final int REQUEST_CODE = 1;
//	private static final int REQUEST_ADDSCH_CODE =3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("fragment", "Wan_OnCreate");
		super.onCreate(savedInstanceState);
		/**
		 * 初始化三个fragment
		 */
		mFragment = new MonthFragment();
		wFragment = new WeekFragment();
		dFragment = new DayFragment();
		/**
		 * 初始化fragment的list列表，将月周日fragment存入list中
		 */
		fragmentlist = new ArrayList<Fragment>();
		fragmentlist.add(wFragment);
		fragmentlist.add(mFragment);
		fragmentlist.add(dFragment);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.wannianli_mainform, container, false);
		/**
		 * 设置title上的日期显示。
		 */
		InitImageView();
		InitViewPager();
		SetDateTimeFortitle(CURRENT_INDEX);
		/**
		 * 添加监听器，定位日期，弹出对话框
		 */
		RelativeLayout dateTimelayout = (RelativeLayout) view
				.findViewById(R.id.DateTimeInfo);
		dateTimelayout.setOnClickListener(new MyTitleOnClick());
		/**
		 *  用户登录点击
		 */
		FrameLayout userLogin = (FrameLayout) view
				.findViewById(R.id.layout_iconuser);
		userLogin.setOnClickListener(new MyTitleOnClick());
		/**
		 * 添加卡片点击响应，设置监听
		 */
		LinearLayout addmodel = (LinearLayout)view.findViewById(R.id.linear_addModel);
		addmodel.setOnClickListener(new MyTitleOnClick());
		return view;
	}
	/**
	 * @author LSHM
	 *点击监听器类，对mainmonth主界面title中的三个布局进行点击监听
	 */
	class MyTitleOnClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.linear_addModel://添加卡片
				new AddCardShowLayout(getActivity(), mFragment).openPopWindow();
				break;
			case R.id.DateTimeInfo://定位日期
				// 弹出日历定位对话框
				DatePickerDialogFrag dialogFrag = new DatePickerDialogFrag();
				dialogFrag.setTargetFragment(TabFragmentWan.this,0);
				dialogFrag.show(getActivity().getSupportFragmentManager(),
						"datepicker");
				break;
			case R.id.layout_iconuser://用户登录
				Intent intent = new Intent();
				intent.setClass(TabFragmentWan.this.getActivity(),
						LoginRegisteActivity.class);
				// TabFragmentWan.this.getActivity().startActivity(intent);
				startActivityForResult(intent,MainActivity.getRequestCodeWan());
				break;
				
			}
			
		}
		
	}

	/**
	 * @param mode
	 *0:代表月视图的title显示，1：代表周视图的显示，2：代表日视图的显示
	 */
	public void SetDateTimeFortitle(int mode) {
		TextView tvS = (TextView) view.findViewById(R.id.titile_gre_ym);
		TextView tvL = (TextView) view.findViewById(R.id.title_lun_ym);
		String str1 = calTools.getCurrentYear() + "."
				+ String.format("%02d", calTools.getCurrentMonth() + 1) + "."
				+ String.format("%02d", calTools.getTouchDay());
		tvS.setText(str1);
		tvL.setText(calTools.GetCurLunar(mode));
	}
	/**
	 * 初始化ViewPager，1）设置适配器，2）添加view布局到List<View>中
	 */
	private void InitViewPager() {
		viewPager = (ViewPager) view.findViewById(R.id.pager);
		//fragmentlist已经在oncreate中初始化了
		viewPager.setAdapter(new MyViewPagerAdapter(this
				.getChildFragmentManager(), fragmentlist));
		viewPager.setCurrentItem(IMG_MONTH);

		CURRENT_INDEX = IMG_MONTH;
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		viewPager.setOffscreenPageLimit(0);
	}

	/**
	 * 初始化imagview控件，并且添加点击事件
	 */
	private void InitImageView() {
		imageView1 = (ImageView) view.findViewById(R.id.iv_point_1);
		imageView2 = (ImageView) view.findViewById(R.id.iv_point_2);
		imageView3 = (ImageView) view.findViewById(R.id.iv_point_3);

		imageView1.setOnClickListener(new MyOnClickListener(IMG_WEEK));
		imageView2.setOnClickListener(new MyOnClickListener(IMG_MONTH));
		imageView3.setOnClickListener(new MyOnClickListener(IMG_DAY));
	}

	/**
	 * 添加三个监听器，对ImgView类型的page界面指示图标进行监听。
	 */
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			viewPager.setCurrentItem(index);
		}
	}

	/**
	 * @author LSHM ViewPager的适配器类，将List<View>传入ViewPager中
	 */
	public class MyViewPagerAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragments;

		// private FragmentManager fm;

		public MyViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			// this.fm=fm;
			this.fragments = fragments;

		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			return super.instantiateItem(container, position);
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return PagerAdapter.POSITION_NONE;
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return (fragments == null || fragments.size() == 0 ? null
					: fragments.get(arg0));
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragments == null ? 0 : fragments.size();
		}
	}

	/**
	 * @author LSHM 此类实现页面改变监听接口
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
		}

		/*
		 * (non-Javadoc)页面变换响应函数，对此函数重写，使得对应指示图标同步变换
		 * 
		 * @see
		 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected
		 * (int)
		 */
		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case IMG_WEEK:
				setSelectedImg(IMG_WEEK);
				CURRENT_INDEX = IMG_WEEK;
				SetDateTimeFortitle(CURRENT_INDEX);
				break;
			case IMG_MONTH:
				setSelectedImg(IMG_MONTH);
				CURRENT_INDEX = IMG_MONTH;
				SetDateTimeFortitle(CURRENT_INDEX);
				break;
			case IMG_DAY:
				setSelectedImg(IMG_DAY);
				CURRENT_INDEX = IMG_DAY;
				SetDateTimeFortitle(CURRENT_INDEX);
				break;
			}
		}
	}

	/**
	 * @param id
	 *            ，则函数用于图标单一显示
	 */
	private void setSelectedImg(int id) {
		switch (CURRENT_INDEX) {
		case IMG_WEEK:
			imageView1.setImageResource(R.drawable.main_point_z);
			break;
		case IMG_MONTH:
			imageView2.setImageResource(R.drawable.main_point_y);
			break;
		case IMG_DAY:
			imageView3.setImageResource(R.drawable.main_point_r);
			break;
		}
		switch (id) {
		case IMG_WEEK:
			imageView1.setImageResource(R.drawable.main_point_z_sel);
			break;
		case IMG_MONTH:
			imageView2.setImageResource(R.drawable.main_point_y_sel);
			break;
		case IMG_DAY:
			imageView3.setImageResource(R.drawable.main_point_r_sel);
			break;
		}
	}

	public static int getCURRENT_INDEX() {
		return CURRENT_INDEX;
	}

	public MonthFragment getmFragment() {
		return mFragment;
	}

	public WeekFragment getwFragment() {
		return wFragment;
	}

	public DayFragment getdFragment() {
		return dFragment;
	}

	public ViewPager getViewPager() {
		return viewPager;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		String temp = null;
		if(requestCode == MainActivity.getRequestCodeWan()){
			if(resultCode == Activity.RESULT_OK){
				
				Bundle extras = data.getExtras();
				if(extras != null){
					temp = extras.getString("user");
					if(temp !=null && temp.equals("QQ")){
						ImageView imView = (ImageView)getActivity().findViewById(R.id.img_userPic);
						
						imView.setImageResource(R.drawable.image_fu_show);
					}
				}
			}
		}else if(requestCode == MainActivity.getRequestCodeAddsch()){//日视图添加日程是activityResult
			if(resultCode == Activity.RESULT_OK){//添加日程完成
				dFragment.setSchedule();//刷新日程信息
				mFragment.refresh1();
			}else if(resultCode == Activity.RESULT_CANCELED){//返回
				//不做任何操作，表示没有加日程
			}
		}else if(requestCode == MainActivity.getRequestCodeEditsch()){
			if(resultCode == Activity.RESULT_OK){
				String action = data.getStringExtra("action");
				if(action.equals("delete")){//删除
					dFragment.setSchedule();
					mFragment.refresh1();
				}else if(action.equals("finish")){//修改
					dFragment.setSchedule();
					mFragment.refresh1();
				}else if(action.equals("back")){//返回
					//不做任何操作，表示没有加日程
				}
			}else if(resultCode == Activity.RESULT_CANCELED){//返回
				//不做任何操作，表示没有加日程
			}
		}
		//super.onActivityResult(requestCode, resultCode, data);
	}
	
	
}
