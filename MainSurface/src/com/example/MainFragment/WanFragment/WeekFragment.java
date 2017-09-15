package com.example.MainFragment.WanFragment;

import java.sql.Date;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.example.AddSchedule.SchduleForWeek;
import com.example.MainFragment.WanFragment.Month.TabFragmentWan;
import com.example.calDrawView.CalendarTools;
import com.example.mainsurface.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeekFragment extends Fragment {
	private View view;
	private CalendarTools caTools;
	private Button preweekbtn;
	private Button nextweekbtn;
	private Button nowweekbtn;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		caTools = new CalendarTools();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.calview_week_list, container, false);
		setTextTime();

		/**
		 * ���������ܰ�ť�����¼�
		 */
		setOnClickforWeekbtn();
		/**
		 * ����ÿһ�ܲ��ֵĵ���¼�����ת������ͼ��
		 */
		setLiearWeekOnClick();
		
		return view;
	}
	public void refresh(){
		setTextTime();
	}
	private void setOnClickforWeekbtn(){
		preweekbtn = (Button)view.findViewById(R.id.pre_week_btn_1);
		nextweekbtn =(Button)view.findViewById(R.id.next_week_btn);
		nowweekbtn= (Button)view.findViewById(R.id.now_week_btn);

		preweekbtn.setOnClickListener(new MyBtnOnClick());
		nextweekbtn.setOnClickListener(new MyBtnOnClick());
		nowweekbtn.setOnClickListener(new MyBtnOnClick());
	}
	/**
	 * @author LSHM
	 *�����ܵ���¼���ͬ����������ͼ�����ݣ���ΪviewPagerԤ����1���������ͼ������
	 */
	class MyBtnOnClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.pre_week_btn_1:
				caTools.SetPre_NextWeek(true);
				((TabFragmentWan)getParentFragment()).SetDateTimeFortitle(TabFragmentWan.getCURRENT_INDEX());
				refresh();//��������ͼ
				refreshMonthView();//ͬ����������ͼ
				break;
			case R.id.next_week_btn:
				caTools.SetPre_NextWeek(false);
				((TabFragmentWan)getParentFragment()).SetDateTimeFortitle(TabFragmentWan.getCURRENT_INDEX());
				refresh();//��������ͼ
				refreshMonthView();//ͬ����������ͼ
				break;
			case R.id.now_week_btn:
				caTools.SetReallyTime();
				((TabFragmentWan)getParentFragment()).SetDateTimeFortitle(TabFragmentWan.getCURRENT_INDEX());
				refresh();//��������ͼ
				refreshMonthView();//ͬ����������ͼ
				break;
			}
			
		}
	}
	private void refreshMonthView(){
		TabFragmentWan wan=(TabFragmentWan)this.getParentFragment();
		wan.getmFragment().refresh();
	}
	
	private void setLiearWeekOnClick(){
		//����
		LinearLayout weekLayout = (LinearLayout)view.findViewById(R.id.Sunday_content);
		weekLayout.setOnClickListener(new MyLinearOnClick());
		//��һ
		weekLayout =(LinearLayout)view.findViewById(R.id.Monday_content);
		weekLayout.setOnClickListener(new MyLinearOnClick());
		//��һ
		weekLayout =(LinearLayout)view.findViewById(R.id.Tuesday_content);
		weekLayout.setOnClickListener(new MyLinearOnClick());
		//��һ
		weekLayout =(LinearLayout)view.findViewById(R.id.Wednesday_content);
		weekLayout.setOnClickListener(new MyLinearOnClick());
		//��һ
		weekLayout =(LinearLayout)view.findViewById(R.id.Thursday_content);
		weekLayout.setOnClickListener(new MyLinearOnClick());
		//��һ
		weekLayout =(LinearLayout)view.findViewById(R.id.Friday_content);
		weekLayout.setOnClickListener(new MyLinearOnClick());
		//��һ
		weekLayout =(LinearLayout)view.findViewById(R.id.Saturday_content);
		weekLayout.setOnClickListener(new MyLinearOnClick());
		
	}
	private void setTextTime(){
		String[] weekStrs=caTools.getOneWeek();
		String[] festival = caTools.getFestival();
		for(int i=0;i<weekStrs.length;i++){
			switchText(i,weekStrs[i],festival[i]);
		}
		/**
		 * ����Ǳ������ý��ղ�����������˸
		 */
		if(CalendarTools.getCurrentdayofweek() != -1){
			Log.i("time",String.valueOf(-1));
			setFlashForText(CalendarTools.getCurrentdayofweek());
		}else{
			cleanSendTimerTask();
			Log.i("time",String.valueOf(1));
		}
		/**
		 * �����ճ���ʾ
		 */
		getScheduleforlinear();
	}
	private void setFestivaltext(TextView text,String festival){
		if(!festival.equals("")){
			text.setVisibility(View.VISIBLE);
			text.setText(festival);
		}else{
			text.setVisibility(View.GONE);
		}
	}
	private void switchText(int i,String text,String festival){
		TextView tView=null;
		switch(i){
		case 0:
			tView =(TextView) view.findViewById(R.id.Sunfortime);
			tView.setText(text);
			tView=(TextView)view.findViewById(R.id.SunforFestival);
			setFestivaltext(tView,festival);
			break;
		case 1:
			tView =(TextView) view.findViewById(R.id.Monfortime);
			tView.setText(text);
			tView=(TextView)view.findViewById(R.id.MonforFestival);
			setFestivaltext(tView,festival);
			break;
		case 2:
			tView =(TextView) view.findViewById(R.id.Tuesfortime);
			tView.setText(text);
			tView=(TextView)view.findViewById(R.id.TuesforFestival);
			setFestivaltext(tView,festival);
			break;
		case 3:
			tView =(TextView) view.findViewById(R.id.Wednesfortime);
			tView.setText(text);
			tView=(TextView)view.findViewById(R.id.WednesforFestival);
			setFestivaltext(tView,festival);
			break;
		case 4:
			tView =(TextView) view.findViewById(R.id.Thursfortime);
			tView.setText(text);
			tView=(TextView)view.findViewById(R.id.ThursforFestival);
			setFestivaltext(tView,festival);
			break;
		case 5:
			tView =(TextView) view.findViewById(R.id.Frifortime);
			tView.setText(text);
			tView=(TextView)view.findViewById(R.id.FriforFestival);
			setFestivaltext(tView,festival);
			break;
		case 6:
			tView =(TextView) view.findViewById(R.id.Saturfortime);
			tView.setText(text);
			tView=(TextView)view.findViewById(R.id.SaturforFestival);
			setFestivaltext(tView,festival);
			break;
		}
	}

	class MyLinearOnClick implements OnClickListener{
		//��������linearLayout��Ӧ�¼�
		@Override
		public void onClick(View v) {
			//ÿ�εõ��ܶ������ɱ��ܵ�һ�������
			//first����Ϊ3���飬0���꣬1���£�2����
			int[] first=CalendarTools.getFirstdayofweek();
			caTools.SetTouchTime(first[0], first[1]);
			caTools.SetTouchDay(first[2]);
			switch(v.getId()){
			case R.id.Sunday_content:
				getNextdays(0);
				((TabFragmentWan)getParentFragment()).SetDateTimeFortitle(TabFragmentWan.getCURRENT_INDEX());
				((TabFragmentWan)getParentFragment()).getViewPager().setCurrentItem(2);//��ת������ͼ
				break;
			case R.id.Monday_content:
				getNextdays(1);
				((TabFragmentWan)getParentFragment()).SetDateTimeFortitle(TabFragmentWan.getCURRENT_INDEX());
				((TabFragmentWan)getParentFragment()).getViewPager().setCurrentItem(2);//��ת������ͼ
				break;
			case R.id.Tuesday_content:
				getNextdays(2);
				((TabFragmentWan)getParentFragment()).SetDateTimeFortitle(TabFragmentWan.getCURRENT_INDEX());
				((TabFragmentWan)getParentFragment()).getViewPager().setCurrentItem(2);//��ת������ͼ
				break;
			case R.id.Wednesday_content:
				getNextdays(3);
				((TabFragmentWan)getParentFragment()).SetDateTimeFortitle(TabFragmentWan.getCURRENT_INDEX());
				((TabFragmentWan)getParentFragment()).getViewPager().setCurrentItem(2);//��ת������ͼ
				break;
			case R.id.Thursday_content:
				getNextdays(4);
				((TabFragmentWan)getParentFragment()).SetDateTimeFortitle(TabFragmentWan.getCURRENT_INDEX());
				((TabFragmentWan)getParentFragment()).getViewPager().setCurrentItem(2);//��ת������ͼ
				break;
			case R.id.Friday_content:
				getNextdays(5);
				((TabFragmentWan)getParentFragment()).SetDateTimeFortitle(TabFragmentWan.getCURRENT_INDEX());
				((TabFragmentWan)getParentFragment()).getViewPager().setCurrentItem(2);//��ת������ͼ
				break;
			case R.id.Saturday_content:
				getNextdays(6);
				((TabFragmentWan)getParentFragment()).SetDateTimeFortitle(TabFragmentWan.getCURRENT_INDEX());
				((TabFragmentWan)getParentFragment()).getViewPager().setCurrentItem(2);//��ת������ͼ
				break;
				
			}
		}	
	}
	private void getNextdays(int num){
		for(int i=0;i<num;i++)
			caTools.SetNextDay();
	}
	private void setFlashForText(int num){
		TextView tView =null;
		String[] week_Xingqi = {"������","����һ","���ڶ�","������","������","������","������"};
		switch(num){
		case 0:
			tView = (TextView)view.findViewById(R.id.tv_week_Sun);
			spark(tView,week_Xingqi[0]);
			break;
		case 1:
			tView = (TextView)view.findViewById(R.id.tv_week_Mon);
			spark(tView,week_Xingqi[1]);
			break;
		case 2:
			tView = (TextView)view.findViewById(R.id.tv_week_Tue);
			spark(tView,week_Xingqi[2]);
			break;
		case 3:
			tView = (TextView)view.findViewById(R.id.tv_week_Wed);
			spark(tView,week_Xingqi[3]);
			break;
		case 4:
			tView = (TextView)view.findViewById(R.id.tv_week_Thu);
			spark(tView,week_Xingqi[4]);
			break;
		case 5:
			tView = (TextView)view.findViewById(R.id.tv_week_Fri);
			spark(tView,week_Xingqi[5]);
			break;
		case 6:
			tView = (TextView)view.findViewById(R.id.tv_week_Sat);
			spark(tView,week_Xingqi[6]);
			break;
		}
	}
	private Timer timer =null;
	private TimerTask taskcc=null;
	private boolean change =false;
	private TextView remeberText = null;
	private String remeberWeek = null;
	
	@SuppressLint("HandlerLeak")
	final Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 0:
				if(remeberText !=null){//�ָ���ʾ����
					 remeberText.setText(remeberWeek);
					// remeberText =null;
				 }
				break;
			case 1:
				if(remeberText !=null){//�ָ���ʾ����
					 remeberText.setText("����");
					 //remeberText =null;
				 }
				break;
			case 2:
				if(remeberText !=null){//�ָ���ʾ����
					 remeberText.setText(remeberWeek);
					 remeberText =null;
				 }
				break;
			}
			//super.handleMessage(msg);
		}
		
	};
	
	 public void spark(TextView text,String str) {
		remeberText =text;
		remeberWeek =str;
		if(timer ==null && taskcc ==null){
			timer = new Timer();
			taskcc = new TimerTask() {
	            public void run() {
	            	Message message = new Message(); 
	            	if(change){ 
                    	change =false;
                    	message.what = 0;
                    	handler.sendMessage(message);
                    }else{
                    	change =true;
                    	message.what = 1;
                    	handler.sendMessage(message);
                    }
	            }
	        };
	        timer.schedule(taskcc, 1, 1000);
		}
    }
	 private void cleanSendTimerTask() {
		 if (taskcc != null) {
        	 taskcc.cancel();
        	 taskcc = null;
         }
         if (timer != null) {
        	 timer.cancel();
        	 timer.purge();
        	 timer = null;
         }	 
         Message message = new Message(); 
         message.what = 2;
         handler.sendMessage(message);
         //�ָ��ı�״̬
         change = false;
	 }

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		cleanSendTimerTask();
	}
	public void getScheduleforlinear(){
		int[] firstday =CalendarTools.getFirstdayofweek();
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(firstday[0],
				firstday[1],
				firstday[2]);
		Date date = new java.sql.Date(cal.getTimeInMillis());
		SchduleForWeek schduleForWeek = new SchduleForWeek(getActivity(), date, view);
		schduleForWeek.ScheduleTolist();
	}
	 
}
