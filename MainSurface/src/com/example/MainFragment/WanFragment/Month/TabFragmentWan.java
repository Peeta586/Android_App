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

	private View view;// �ܵ�view����
	private ViewPager viewPager;
	private ImageView imageView1, imageView2, imageView3;// ������ָʾͼ��

	private static CalendarTools calTools = new CalendarTools();
	/**
	 * ����ҳ��itemֵ
	 */
	private final static int IMG_WEEK = 0;
	private final static int IMG_MONTH = 1;
	private final static int IMG_DAY = 2;
	private static int CURRENT_INDEX;
	/**
	 * ҳ��list
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
		 * ��ʼ������fragment
		 */
		mFragment = new MonthFragment();
		wFragment = new WeekFragment();
		dFragment = new DayFragment();
		/**
		 * ��ʼ��fragment��list�б���������fragment����list��
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
		 * ����title�ϵ�������ʾ��
		 */
		InitImageView();
		InitViewPager();
		SetDateTimeFortitle(CURRENT_INDEX);
		/**
		 * ��Ӽ���������λ���ڣ������Ի���
		 */
		RelativeLayout dateTimelayout = (RelativeLayout) view
				.findViewById(R.id.DateTimeInfo);
		dateTimelayout.setOnClickListener(new MyTitleOnClick());
		/**
		 *  �û���¼���
		 */
		FrameLayout userLogin = (FrameLayout) view
				.findViewById(R.id.layout_iconuser);
		userLogin.setOnClickListener(new MyTitleOnClick());
		/**
		 * ��ӿ�Ƭ�����Ӧ�����ü���
		 */
		LinearLayout addmodel = (LinearLayout)view.findViewById(R.id.linear_addModel);
		addmodel.setOnClickListener(new MyTitleOnClick());
		return view;
	}
	/**
	 * @author LSHM
	 *����������࣬��mainmonth������title�е��������ֽ��е������
	 */
	class MyTitleOnClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.linear_addModel://��ӿ�Ƭ
				new AddCardShowLayout(getActivity(), mFragment).openPopWindow();
				break;
			case R.id.DateTimeInfo://��λ����
				// ����������λ�Ի���
				DatePickerDialogFrag dialogFrag = new DatePickerDialogFrag();
				dialogFrag.setTargetFragment(TabFragmentWan.this,0);
				dialogFrag.show(getActivity().getSupportFragmentManager(),
						"datepicker");
				break;
			case R.id.layout_iconuser://�û���¼
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
	 *0:��������ͼ��title��ʾ��1����������ͼ����ʾ��2����������ͼ����ʾ
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
	 * ��ʼ��ViewPager��1��������������2�����view���ֵ�List<View>��
	 */
	private void InitViewPager() {
		viewPager = (ViewPager) view.findViewById(R.id.pager);
		//fragmentlist�Ѿ���oncreate�г�ʼ����
		viewPager.setAdapter(new MyViewPagerAdapter(this
				.getChildFragmentManager(), fragmentlist));
		viewPager.setCurrentItem(IMG_MONTH);

		CURRENT_INDEX = IMG_MONTH;
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		viewPager.setOffscreenPageLimit(0);
	}

	/**
	 * ��ʼ��imagview�ؼ���������ӵ���¼�
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
	 * �����������������ImgView���͵�page����ָʾͼ����м�����
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
	 * @author LSHM ViewPager���������࣬��List<View>����ViewPager��
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
	 * @author LSHM ����ʵ��ҳ��ı�����ӿ�
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
		 * (non-Javadoc)ҳ��任��Ӧ�������Դ˺�����д��ʹ�ö�Ӧָʾͼ��ͬ���任
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
	 *            ����������ͼ�굥һ��ʾ
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
		}else if(requestCode == MainActivity.getRequestCodeAddsch()){//����ͼ����ճ���activityResult
			if(resultCode == Activity.RESULT_OK){//����ճ����
				dFragment.setSchedule();//ˢ���ճ���Ϣ
				mFragment.refresh1();
			}else if(resultCode == Activity.RESULT_CANCELED){//����
				//�����κβ�������ʾû�м��ճ�
			}
		}else if(requestCode == MainActivity.getRequestCodeEditsch()){
			if(resultCode == Activity.RESULT_OK){
				String action = data.getStringExtra("action");
				if(action.equals("delete")){//ɾ��
					dFragment.setSchedule();
					mFragment.refresh1();
				}else if(action.equals("finish")){//�޸�
					dFragment.setSchedule();
					mFragment.refresh1();
				}else if(action.equals("back")){//����
					//�����κβ�������ʾû�м��ճ�
				}
			}else if(resultCode == Activity.RESULT_CANCELED){//����
				//�����κβ�������ʾû�м��ճ�
			}
		}
		//super.onActivityResult(requestCode, resultCode, data);
	}
	
	
}
