package com.example.Constellation;

import com.example.mainsurface.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class ConstellationActivity extends Activity {
	private TableLayout tablayout;
	private LinearLayout title;
	private TextView title_tV;
	private ViewFlipper flipper;

	// �����һ���������飬����ΪViewFlipperָ���л�����Ч��
	private Animation[] animations = new Animation[4];
	private static boolean if_openTab = false;
//	private final static String[] name_const = { "������", "��ţ��", "˫����", "��з��",
//			"ʨ����", "��Ů��", "��ƽ��", "��Ы��", "������", "Ħ����", "ˮƿ��", "˫����" };
//	private final static String[] time_const = { "3.21-4.20", "4.21-5.20",
//			"5.21-6.21", "6.22-7.22", "7.23-8.22", "8.23-9.22", "9.23-10.22",
//			"10.23-11.21", "11.22-12.21", "12.22-1.19", "1.20-2.18",
//			"2.19-3.20" };
	private String[] name_const=new String[12];
	private String[] time_const=new String[12];
	private int[] con_icon = { R.drawable.cons_aries, R.drawable.cons_taurus,
			R.drawable.cons_gemini, R.drawable.cons_cancer,
			R.drawable.cons_leo, R.drawable.cons_virgo, R.drawable.cons_libra,
			R.drawable.cons_scorpio, R.drawable.cons_sagittarius,
			R.drawable.cons_capricorn, R.drawable.cons_aquarius,
			R.drawable.cons_pisces };
	/**
	 * tabhostһЩ����
	 */
	// private String[] tabTag ={"tab1","tab2","tab3"};
	private String[] tab_label = { "��������", "��������", "��������" };
	private View todayView, tommoView, weekView;
	private View[] tabviews = new View[] { todayView, tommoView, weekView };
	private int tabids[] = { R.id.today_fliper, R.id.tommo_fliper,
			R.id.week_fliper };
	private TabWidget tabWidget;
	private ScrollableViewGroup group1,group2,group3;
	/**
	 * ����·��
	 */
	private static int CurrentView =-1;//ViewGroup�е�ǰview
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.constellation_app);
		//�������������ʱ��ε�����
		name_const =this.getResources().getStringArray(R.array.astro_name);
		time_const =this.getResources().getStringArray(R.array.astro_date);
		/**
		 * �����е�viewflipper�����»�����ʵ��������tablelayout������
		 */
		InitFlipper();// ��ʼ��tabhost���ڵ�viewflipper��
		InitTabLayout();// �ҵ�����
		InitBackShare();// ���������
		InitTabFlippers();//��ʼ������ViewGroup
		InitTabHost();// ��ʼ��tabhost���õ�detector
	}

	private void InitTabLayout() {
		tablayout = (TableLayout) findViewById(R.id.tableLayout);
		// titleTextView
		title_tV = (TextView) findViewById(R.id.topbarTitle);
		title_tV.setText("ħЫ��");
		// ��ӵ���¼�
		title = (LinearLayout) findViewById(R.id.clickPane);
		title.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (if_openTab) {
					if(CurrentView !=-1)
						title_tV.setText(name_const[CurrentView]);
					else
						title_tV.setText(name_const[0]);
					flipper.setInAnimation(animations[2]);
					flipper.setOutAnimation(animations[1]);
					flipper.showNext();
					// tablayout.startAnimation(animation_hide);
					if_openTab = false;
				} else {
					title_tV.setText("�ҵ�����");
					flipper.setInAnimation(animations[0]);
					flipper.setOutAnimation(animations[3]);
					flipper.showPrevious();
					// tablayout.startAnimation(animation_show);
					if_openTab = true;
				}
			}
		});
		InitTableContent();
		tablayout.setOnTouchListener(new MyOnTouchEvent());
	}
	class MyOnTouchEvent implements  OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				int name_num =getClickWhich(event.getX(),event.getY());
				if(name_num>=0){
					title_tV.setText(name_const[name_num]);
					setCurrentView(name_num);
				}
				if(CurrentView !=-1)
					title_tV.setText(name_const[CurrentView]);
				else
					title_tV.setText(name_const[0]);
				flipper.setInAnimation(animations[2]);
				flipper.setOutAnimation(animations[1]);
				flipper.showNext();
				if_openTab = false;
			}
			return true;
		}
		private int getClickWhich(float x,float y){
			int cellwidth=tablayout.getMeasuredWidth()/3;
			int cellheight=tablayout.getMeasuredHeight()/4;
			int Xnum=0,Ynum=0;
			for(int i=1;i<=3;i++){
				if(x<=cellwidth*i && x>cellwidth *(i-1)){
					Xnum =i;
					break;
				}
			}
			for(int i=1;i<=4;i++){
				if(y<=cellheight*i && y>cellheight *(i-1)){
					Ynum =i-1;
					break;
				}
			}
			return Xnum+Ynum*3-1;
		}
		
	}
	private void setCurrentView(int currentV){
		CurrentView =currentV;
		group1.setCurrentView(currentV);
		group2.setCurrentView(currentV);
		group3.setCurrentView(currentV);
	}
	/**
	 * ��ʼ��tableLayout������
	 */
	private void InitTableContent() {
		// �����ĸ�TableRow��ÿһ������������
		for (int i = 0; i < tablayout.getChildCount(); i = i + 2) {
			TableRow row = (TableRow) tablayout.getChildAt(i);
			for (int j = 0; j < 3; j++) {
				ImageView iconImg = (ImageView) row.getChildAt(j * 2)
						.findViewById(R.id.consIcon);
				iconImg.setImageResource(con_icon[i / 2 * 3 + j]);
				TextView tView = (TextView) row.getChildAt(j * 2).findViewById(
						R.id.consName);
				tView.setText(name_const[i / 2 * 3 + j]);
				TextView tView2 = (TextView) row.getChildAt(j * 2)
						.findViewById(R.id.consTime);
				tView2.setText(time_const[i / 2 * 3 + j]);
			}
		}
	}

	private void InitBackShare() {
		// ���ذ�ť��ʼ�������
		FrameLayout myBack = (FrameLayout) findViewById(R.id.topbarBackBtn);
		myBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// ����ť��ʼ�������
		LinearLayout myShare = (LinearLayout) findViewById(R.id.topbar_share_btn);
		myShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			//	TextView tView =(TextView)findViewById(R.id.all_doc);
				//tView.setText(text)
				
			}
		});
	}

	private void InitFlipper() {
		flipper = (ViewFlipper) findViewById(R.id.viewflipper_contellation);
		flipper.showNext();
		// ��ʼ��Animation����
		animations[0] = AnimationUtils.loadAnimation(this, R.anim.top_in);
		animations[0].setFillAfter(true);
		animations[1] = AnimationUtils.loadAnimation(this, R.anim.top_out);
		animations[1].setFillAfter(true);
		animations[2] = AnimationUtils.loadAnimation(this, R.anim.bottom_in);
		animations[2].setFillAfter(true);
		animations[3] = AnimationUtils.loadAnimation(this, R.anim.bottom_out);
		animations[3].setFillAfter(true);
	}

	/**
	 * ��ʼ��tabhost
	 */
	private void InitTabHost() {
		TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup();
		tabWidget = (TabWidget) tabHost.findViewById(android.R.id.tabs);
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				if (tabId.equals(tab_label[0])) {//��������
					tabWidget
							.setBackgroundResource(R.drawable.fortune_tabs_left);
				} else if (tabId.equals(tab_label[1])) {//��������
					tabWidget
							.setBackgroundResource(R.drawable.fortune_tabs_center);	
				} else if (tabId.equals(tab_label[2])) {//��������
					tabWidget
							.setBackgroundResource(R.drawable.fortune_tabs_right);
				}
			}
		});
		for (int i = 0; i < tabviews.length; i++) {
			tabviews[i] = (View) LayoutInflater.from(this).inflate(
					R.layout.tab_host_cons, null);
			TextView tView = (TextView) tabviews[i]
					.findViewById(R.id.tab_label);
			tView.setText(tab_label[i]);
			tabHost.addTab(tabHost.newTabSpec(tab_label[i])
					.setIndicator(tabviews[i]).setContent(tabids[i]));
		}
	}

	private void InitTabFlippers() {
		group1 = (ScrollableViewGroup) findViewById(R.id.today_fliper);
		group2 = (ScrollableViewGroup) findViewById(R.id.tommo_fliper);
		group3 = (ScrollableViewGroup) findViewById(R.id.week_fliper);
		//Ϊ��������flipper���view
		for (int i = 0; i < con_icon.length; i++) {
			View view = LayoutInflater.from(this).inflate(
					R.layout.today_futune, null);
			ImageView iconImg = (ImageView) view.findViewById(R.id.consIcon);
			iconImg.setImageResource(con_icon[i]);
			TextView tView = (TextView) view.findViewById(R.id.consName);
			tView.setText(name_const[i]);
			TextView tView2 = (TextView) view.findViewById(R.id.consTime);
			tView2.setText(time_const[i]);
			
			group1.addView(view);
			//tommoFlipper.addView(view);������ӣ���Ϊview�Ѿ���һ��parentview��
		}
		//����flipper���view
		for (int i = 0; i < con_icon.length; i++) {
			View view = LayoutInflater.from(this).inflate(
					R.layout.today_futune, null);
			ImageView iconImg = (ImageView) view.findViewById(R.id.consIcon);
			iconImg.setImageResource(con_icon[i]);
			TextView tView = (TextView) view.findViewById(R.id.consName);
			tView.setText(name_const[i]);
			TextView tView2 = (TextView) view.findViewById(R.id.consTime);
			tView2.setText(time_const[i]);
			
			group2.addView(view);
		}
		//Ϊ����flipper���view
		for (int i = 0; i < con_icon.length; i++) {
			View view = LayoutInflater.from(this).inflate(
					R.layout.week_fortune, null);
			ImageView iconImg = (ImageView) view.findViewById(R.id.consIcon);
			iconImg.setImageResource(con_icon[i]);
			TextView tView = (TextView) view.findViewById(R.id.consName);
			tView.setText(name_const[i]);
			TextView tView2 = (TextView) view.findViewById(R.id.consTime);
			tView2.setText(time_const[i]);
			
			group3.addView(view);
		}
		group1.setOnCurrentViewChangedListener(new MyCurrentViewChange());
		group2.setOnCurrentViewChangedListener(new MyCurrentViewChange());
		group3.setOnCurrentViewChangedListener(new MyCurrentViewChange());
	}
	class MyCurrentViewChange implements ScrollableViewGroup.OnCurrentViewChangedListener{
		@Override
		public void onCurrentViewChanged(View view, int currentview) {
			
			switch(view.getId()){
			case R.id.today_fliper://��������
				//View view1=group1.getChildAt(currentview);
				title_tV.setText(name_const[currentview]);
				if(currentview !=CurrentView){
				 new AsyTaskConsFromInternet(view,currentview).execute("day",String.valueOf(currentview));
				CurrentView =currentview;
				group2.setCurrentView(currentview);
				group3.setCurrentView(currentview);
				}
				break;
			case R.id.tommo_fliper://��������
				//View view2=group2.getChildAt(currentview);
				title_tV.setText(name_const[currentview]);
				if(currentview !=CurrentView){
				 new AsyTaskConsFromInternet(view,currentview).execute("tomorrow",String.valueOf(currentview));
				 CurrentView =currentview;
				group1.setCurrentView(currentview);
				group3.setCurrentView(currentview);
				}
				break;
			case R.id.week_fliper://��������
				//View view3=group3.getChildAt(currentview);
				title_tV.setText(name_const[currentview]);
				if(currentview !=CurrentView){
				 new AsyTaskConsFromInternet(view,currentview).execute("week",String.valueOf(currentview));
				CurrentView =currentview;
				group1.setCurrentView(currentview);
				group2.setCurrentView(currentview);
				}
				break;
			}
			
			
		}
		
	}
}
