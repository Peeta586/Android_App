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
		//���ذ�ť����
		Button back =(Button)findViewById(R.id.set_Back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	/**
	 * ��ÿ���б�Ԫ��ӵ����Ӧ
	 */
	private void InitLayout(){
		//����ͬ�� �� ���ݱ��ݺͻָ�
		RelativeLayout mysetLayout = (RelativeLayout)findViewById(R.id.synchronism_data);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
		mysetLayout =(RelativeLayout)findViewById(R.id.backups_restore);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
		//���ѣ������С��ʿ����
		mysetLayout =(RelativeLayout)findViewById(R.id.remind_setting);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
		mysetLayout =(RelativeLayout)findViewById(R.id.chajian_set);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
		mysetLayout =(RelativeLayout)findViewById(R.id.tieshi_set);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
		//����������������
		mysetLayout =(RelativeLayout)findViewById(R.id.wannian_setting);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
		mysetLayout =(RelativeLayout)findViewById(R.id.weather_set);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
		//��΢��
		mysetLayout =(RelativeLayout)findViewById(R.id.bangding);
		mysetLayout.setOnClickListener(new MyOnClickLayoutL());
		//������£��������ǣ������ĵ����������
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
	 *�б�����Ӧ��
	 */
	class MyOnClickLayoutL implements OnClickListener{
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch(v.getId()){
			case R.id.synchronism_data://����ͬ�����
				break;
			case R.id.backups_restore://���ݱ��ݺͻָ�
				intent.setClass(ApplicationSet.this, DataBackupSetting.class);
				startActivity(intent);
				break;
			case R.id.remind_setting://��������
				break;
			case R.id.chajian_set://�������
				break;
			case R.id.tieshi_set://��ʿ����
				break;
			case R.id.wannian_setting://����������
				break;
			case R.id.weather_set://��������
				break;
			case R.id.bangding://��΢��
				break;
			case R.id.upgradeLayout://�������
				break;
			case R.id.introduction://��������
				break;
			case R.id.impression://�����ĵ�
				break;
			case R.id.feedback://�������
				break;
			}
		}
	}
	
}
