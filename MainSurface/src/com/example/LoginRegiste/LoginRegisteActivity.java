package com.example.LoginRegiste;

import com.example.mainsurface.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginRegisteActivity extends Activity{
	private String url_path = "http://192.168.191.1:8081/UserProject/servlet/JsonAction";
	private Button btn_login;
	private Button btn_password;
	private Button btn_back;
	private EditText user_name;
	private EditText password;
	private ImageView Img_clear_name;
	private ImageView Img_clear_pwd;
	private TextView testText;
	private LinearLayout linearLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_loginreg_activity);
		
		user_name = (EditText)findViewById(R.id.autocompletetv_account);
		password = (EditText)findViewById(R.id.edt_password);
		Img_clear_name =(ImageView)findViewById(R.id.iv_clear_name);
		Img_clear_pwd = (ImageView)findViewById(R.id.iv_clear_psw);
		
		testText =(TextView)findViewById(R.id.testtext);
		linearLayout = (LinearLayout)findViewById(R.id.ll_progress);
		/**Img_clear_pwd.setVisibility(View.VISIBLE);
		 * EditText的change时间，以及清除时间Img_clear_name.setVisibility(View.VISIBLE);//
		 */
		
		user_name.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
					Img_clear_name.setVisibility(View.VISIBLE);//
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		password.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
					Img_clear_pwd.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
	
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
				
			}
		});
		Img_clear_name.setOnClickListener(new SetOnClickListener());
		Img_clear_pwd.setOnClickListener(new SetOnClickListener());
		
		/**
		 * 下面为返回和登录注册按键响应处理事件
		 */
		btn_back = (Button)findViewById(R.id.denglu_btn_back);
		btn_back.setOnClickListener(new SetOnClickListener());
		
		btn_login = (Button)findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new SetOnClickListener());
		
		btn_password = (Button)findViewById(R.id.btn_regist);
		btn_password.setOnClickListener(new SetOnClickListener());
		
	}
	
	class SetOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.iv_clear_name://
				//Img_clear_name.invalidate();
				user_name.setText("");
				Img_clear_name.setVisibility(View.GONE);
				break;
			case R.id.iv_clear_psw://
				//Img_clear_name.setVisibility(View.GONE);
				//Img_clear_pwd.invalidate();
				password.setText("");
				Img_clear_pwd.setVisibility(View.GONE);
				break;
			case R.id.btn_login://登录
				url_path =url_path+ "?action_flag=user";
				url_path =url_path+"&user_name="+user_name.getText().toString();
				url_path =url_path+"&password="+password.getText().toString();
				TestUserPassword testUserPassword = new TestUserPassword(testText,linearLayout, url_path);
				testUserPassword.execute(10000);//参数为TimeOut时间
				break;
			case R.id.btn_regist://注册
				url_path =url_path+ "?action_flag=user";
				url_path =url_path+"&user_name="+user_name.getText().toString();
				url_path =url_path+"&password="+password.getText().toString();
				TestUserPassword testUserPassword1 = new TestUserPassword(testText,linearLayout, url_path);
				testUserPassword1.execute(10000);//参数为TimeOut时间
				break;
			case R.id.denglu_btn_back://返回
				Bundle bundle = new Bundle();
				bundle.putString("user", "QQ");
				Intent mIntent = new Intent();
				mIntent.putExtras(bundle);
				setResult(RESULT_OK,mIntent);
				finish();
				break;
			}
			
		}
		
	}
	
	
}
