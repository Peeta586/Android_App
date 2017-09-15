package com.example.mainsurface;

//import com.example.MainFragment.TabFragmentCha;
import com.example.MainFragment.TabFragmentHuang;
import com.example.MainFragment.TabFragmentMore;
import com.example.MainFragment.WanFragment.Month.TabFragmentWan;
import com.example.Weather.TabFragmentTian;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageSwitcher;
import android.widget.TextView;
public class MainActivity extends FragmentActivity {
	private TextView textcal;
	private TextView textwea;
	private TextView texthuang;
//	private TextView textcha;
	private TextView textmore;
	public static ImageSwitcher ImageBackground;
	public static int[] theme_img = { R.drawable.bg_1, R.drawable.bg_2,
			R.drawable.bg_3, R.drawable.bg_4, R.drawable.bg_5 };
	private SharedPreferences sharedPref;
	
	private static int ScreenWidth =400;
	private static int ScreenHeight=560;
	private static int CURRENT_ID=0 ;
	private final static int REQUEST_CODE_WAN =1;
	private final static int REQUEST_CODE_WEATHER =2;
	private final static int REQUEST_CODE_ADDSCH =3;
	private final static int REQUEST_CODE_EDITSCH =4;
	/* (non-Javadoc)方法前加注释的快捷键：ALT+SHIFT+J
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	private OnClickListener textviewOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			FragmentManager fm = MainActivity.this.getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			switch(v.getId()){
			case R.id.tvCalendar:
				setSelectedTab(R.id.tvCalendar);
				CURRENT_ID = R.id.tvCalendar;
				
				TabFragmentWan myTabWan = new TabFragmentWan();
				ft.replace(R.id.mainlayout, myTabWan,"wannian");
				break;
			case R.id.tvWeather:
				setSelectedTab(R.id.tvWeather);
				CURRENT_ID = R.id.tvWeather;
				TabFragmentTian myTabTian = new TabFragmentTian();
				
				ft.replace(R.id.mainlayout, myTabTian,"tianqi");
				
				//TabFragmentTai myTabTian = new TabFragmentTai();
				break;
			case R.id.tvHuangLi:
				setSelectedTab(R.id.tvHuangLi);
				CURRENT_ID = R.id.tvHuangLi;
				TabFragmentHuang myTabHuang = new TabFragmentHuang();
				
				ft.replace(R.id.mainlayout, myTabHuang,"huangli");
			
				
				break;
//			case R.id.tvSearch:
//				setSelectedTab(R.id.tvSearch);
//				CURRENT_ID = R.id.tvSearch;
//				TabFragmentCha myTabCha = new TabFragmentCha();
//				
//				ft.replace(R.id.mainlayout, myTabCha,"chaxun");
//				//TabFragmentCha myTabCha = new TabFragmentCha();
//				
//				break;
			case R.id.tvMore:
				setSelectedTab(R.id.tvMore);
				CURRENT_ID = R.id.tvMore;
				TabFragmentMore myTabMore = new TabFragmentMore();
				ft.replace(R.id.mainlayout, myTabMore,"more");
				
				//TabFragmentMore myTabMore = new TabFragmentMore();
				break;
			}
			ft.commit();
		}
	};
	/**
	 * 
	private void remove(){
        int count = this.getSupportFragmentManager().getBackStackEntryCount();

        for (int i = 0; i < count; i++) {
            this.getSupportFragmentManager().popBackStack();
        }
    }*/
	private void setSelectedTab(int id){
		switch(CURRENT_ID){
		case R.id.tvCalendar:
			textcal.setSelected(false);
			break;
		case R.id.tvWeather:
			textwea.setSelected(false);
			break;
		case R.id.tvHuangLi:
			texthuang.setSelected(false);
			break;
//		case R.id.tvSearch:
//			textcha.setSelected(false);
//			break;
		case R.id.tvMore:
			textmore.setSelected(false);
			break;
		}
		switch(id){
		case R.id.tvCalendar:
			textcal.setSelected(true);
			break;
		case R.id.tvWeather:
			textwea.setSelected(true);
			break;
		case R.id.tvHuangLi:
			texthuang.setSelected(true);
			break;
//		case R.id.tvSearch:
//			textcha.setSelected(true);
//			break;
		case R.id.tvMore:
			textmore.setSelected(true);
			break;
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		//主题背景设置
		ImageBackground =(ImageSwitcher)findViewById(R.id.ImageBackground);
		sharedPref = this.getSharedPreferences("themeSet", Context.MODE_PRIVATE);
		/**
		 * 设置屏幕宽度变量
		 */
		 InitScreenWidth();
		/**
		 * 初始化底部tab，并设置监听器，监听事件
		 */
		InitTab();
		
		//初始化将万年历设为首页
		TabFragmentWan taFragmentWan = new TabFragmentWan();
		FragmentManager fm =this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.mainlayout,taFragmentWan,"wannian" );
		
		textcal.setSelected(true);
		CURRENT_ID = R.id.tvCalendar;
		ft.commit();
	}
	/**
	 * @return获得屏幕宽度
	 */
	private void InitScreenWidth() {
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay()
				.getMetrics(metric);
		ScreenWidth = metric.widthPixels;
		ScreenHeight = metric.heightPixels;
	}
	private void InitTab(){
		textcal = (TextView)findViewById(R.id.tvCalendar);
		textwea = (TextView)findViewById(R.id.tvWeather);
		texthuang = (TextView)findViewById(R.id.tvHuangLi);
		//textcha = (TextView)findViewById(R.id.tvSearch);
		textmore = (TextView)findViewById(R.id.tvMore);
		
		textcal.setOnClickListener(textviewOnClickListener);
		textwea.setOnClickListener(textviewOnClickListener);
		texthuang.setOnClickListener(textviewOnClickListener);
		//textcha.setOnClickListener(textviewOnClickListener);
		textmore.setOnClickListener(textviewOnClickListener);
	}
	//
	/* (non-Javadoc)必须写这样才能将消息往下传给fragment
	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	public static int getRequestCodeWan() {
		return REQUEST_CODE_WAN;
	}
	public static int getRequestCodeWeather() {
		return REQUEST_CODE_WEATHER;
	}
	public static int getRequestCodeAddsch() {
		return REQUEST_CODE_ADDSCH;
	}
	public static int getScreenWidth() {
		return ScreenWidth;
	}
	public static int getScreenHeight() {
		return ScreenHeight;
	}
	public static int getRequestCodeEditsch() {
		return REQUEST_CODE_EDITSCH;
	}
	@Override
	protected void onStart() {
		super.onStart();//设置主题
		int num = sharedPref.getInt("position",0);
		ImageBackground.setBackgroundResource(theme_img[num]);
	}
	
	
}
