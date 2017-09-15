package com.example.ApplicationSetting;

import com.example.mainsurface.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ApplicationSet extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_activity);
		InitBtnBack();
		InitLayout();
	}
	private void InitBtnBack(){
		//返回按钮设置
		Button back =(Button)findViewById(R.id.set_Back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	/**
	 * 给每个列表单元添加点击相应
	 */
	private void InitLayout(){
		//数据同步 与 数据备份和恢复
		RelativeLayout mysetLayout = (RelativeLayout)findViewById(R.id.synchronism_data);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
		mysetLayout =(RelativeLayout)findViewById(R.id.backups_restore);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
		//提醒，插件，小贴士设置
		mysetLayout =(RelativeLayout)findViewById(R.id.remind_setting);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
		mysetLayout =(RelativeLayout)findViewById(R.id.chajian_set);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
		mysetLayout =(RelativeLayout)findViewById(R.id.tieshi_set);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
		//万年历，天气管理
		mysetLayout =(RelativeLayout)findViewById(R.id.wannian_setting);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
		mysetLayout =(RelativeLayout)findViewById(R.id.weather_set);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
		//绑定微博
		mysetLayout =(RelativeLayout)findViewById(R.id.bangding);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
		//软件更新，关于我们，帮助文档，意见反馈
		mysetLayout =(RelativeLayout)findViewById(R.id.upgradeLayout);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
		mysetLayout =(RelativeLayout)findViewById(R.id.introduction);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
		mysetLayout =(RelativeLayout)findViewById(R.id.impression);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
		mysetLayout =(RelativeLayout)findViewById(R.id.feedback);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
	}
	/**
	 * @author LSHM
	 *列表点击响应类
	 */
	class MyOnClickLayoutL implements OnClickListener{
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch(v.getId()){
			case R.id.synchronism_data://数据同步点击
				break;
			case R.id.backups_restore://数据备份和恢复
				intent.setClass(ApplicationSet.this, DataBackupSetting.class);
				startActivity(intent);
				break;
			case R.id.remind_setting://提醒设置
				break;
			case R.id.chajian_set://插件设置
				break;
			case R.id.tieshi_set://贴士设置
				break;
			case R.id.wannian_setting://万年历管理
				break;
			case R.id.weather_set://天气管理
				break;
			case R.id.bangding://绑定微博
				break;
			case R.id.upgradeLayout://软件更新
				break;
			case R.id.introduction://关于我们
				break;
			case R.id.impression://帮助文档
				break;
			case R.id.feedback://意见反馈
				break;
			}
		}
	}
	
}
