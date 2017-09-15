package com.example.Calculate;

import com.example.mainsurface.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class CalculateActivity extends Activity {
	private LinearLayout first;
	private LinearLayout second;
	private LinearLayout third;
	
	private ToggleButton Btn1;
	private ToggleButton Btn2;
	private ToggleButton Btn3;
	private Button back;
	
	private TextView result1;
	private TextView start1;
	private TextView end1;
	
	private EditText days;
	private TextView start2;
	private TextView result2;
	private RadioButton radio0;
	private RadioButton radio1;
	
	private RadioButton radio2;
	private RadioButton radio3;
	private TextView start3;
	private TextView result3;
	
	private final static int JIAN=0,TUI=1,CHANGE=2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculate_activity);
		
		Init();
		InitButton();
		InitView1();
		InitView2();
		InitView3();
	}
	private void Init(){
		first =(LinearLayout)findViewById(R.id.linearLayout_jiange);
		second =(LinearLayout)findViewById(R.id.linearLayout_tuisuan);
		third =(LinearLayout)findViewById(R.id.linearLayout_yinyang);
	}
	private void InitButton(){
		//导航btn
		Btn1=(ToggleButton)findViewById(R.id.toggleButton1);
		Btn2=(ToggleButton)findViewById(R.id.toggleButton2);
		Btn3=(ToggleButton)findViewById(R.id.toggleButton3);
		Btn1.setOnClickListener(new MyBtnOnClick());
		Btn2.setOnClickListener(new MyBtnOnClick());
		Btn3.setOnClickListener(new MyBtnOnClick());
		//Button
		back =(Button)findViewById(R.id.button1);
		back.setOnClickListener(new MyBtnOnClick());
		
	}
	private void InitView1(){//第一个视图日期间隔计算
		LinearLayout start =(LinearLayout)findViewById(R.id.linearLayout_jiange_start);
		LinearLayout end=(LinearLayout)findViewById(R.id.linearLayout_jiange_end);
		start.setOnClickListener(new MyBtnOnClick());
		end.setOnClickListener(new MyBtnOnClick());
		Button btnSearch =(Button)findViewById(R.id.btn_search);
		btnSearch.setOnClickListener(new MyBtnOnClick());
		result1=(TextView)findViewById(R.id.textView_jiange_result);
		//日期显示text
		start1 =(TextView)findViewById(R.id.textView_jiange_start);
		end1=(TextView)findViewById(R.id.textView_jiange_end);
	}
	private void InitView2(){
		LinearLayout start =(LinearLayout)findViewById(R.id.linearLayout_tuisuan_start);
		start.setOnClickListener(new MyBtnOnClick());
		days=(EditText)findViewById(R.id.editText1);
		start2 =(TextView)findViewById(R.id.textView_tuisuan_start);
		result2=(TextView)findViewById(R.id.textView_tuisuan_result);
		radio0 =(RadioButton)findViewById(R.id.radio0);
		radio1 =(RadioButton)findViewById(R.id.radio1);
		Button btnSearch =(Button)findViewById(R.id.button3);
		btnSearch.setOnClickListener(new MyBtnOnClick());
	}
	private void InitView3(){
		LinearLayout dateLayout=(LinearLayout)findViewById(R.id.linearLayout_yinyang_start);
		dateLayout.setOnClickListener(new MyBtnOnClick());
		start3=(TextView)findViewById(R.id.textView_yinyang_start);
		result3=(TextView)findViewById(R.id.textView_yinyang_result);
		radio2=(RadioButton)findViewById(R.id.radio2);
		radio3=(RadioButton)findViewById(R.id.radio3);
		Button btnSearch =(Button)findViewById(R.id.button4);
		btnSearch.setOnClickListener(new MyBtnOnClick());
	}
	class MyBtnOnClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			/**
			 * 底部控制按钮
			 */
			case R.id.toggleButton1:
				setSelected(JIAN);
				break;
			case R.id.toggleButton2:
				setSelected(TUI);
				break;
			case R.id.toggleButton3:
				setSelected(CHANGE);
				break;
			case R.id.button1:
				finish();
				break;
				/**
				 * 第一个视图的控件响应
				 */
			case R.id.linearLayout_jiange_start:
				break;
			case R.id.linearLayout_jiange_end:
				break;
			case R.id.btn_search:
				break;
				/**
				 * 第二个视图
				 */
			case R.id.linearLayout_tuisuan_start:
				break;
			case R.id.button3://查询
				break;
				/**
				 * 第三个视图
				 */
			case R.id.linearLayout_yinyang_start:
				break;
			case R.id.button4://查询
				break;
			}	
		}
	}
	private void setSelected(int num){
		//现将所有的设成false
		Btn1.setChecked(false);
		Btn2.setChecked(false);
		Btn3.setChecked(false);
		first.setVisibility(View.GONE);
		second.setVisibility(View.GONE);
		third.setVisibility(View.GONE);
		switch(num){
		case JIAN:
			Btn1.setChecked(true);
			first.setVisibility(View.VISIBLE);
			break;
		case TUI:
			Btn2.setChecked(true);
			second.setVisibility(View.VISIBLE);
			break;
		case CHANGE:
			Btn3.setChecked(true);
			third.setVisibility(View.VISIBLE);
			break;
		}
	}
}
