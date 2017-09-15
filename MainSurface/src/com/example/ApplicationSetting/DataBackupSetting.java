package com.example.ApplicationSetting;

import com.example.ImportExport.JsonToSDtxt;
import com.example.mainsurface.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class DataBackupSetting extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data_backup_setting);
		InitBtnBack();
		InitLayout();
	}
	private void InitBtnBack(){
		Button back =(Button)findViewById(R.id.btn_backupAndRecovery_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	private void  InitLayout(){
		RelativeLayout myLayout = (RelativeLayout)findViewById(R.id.data_backup_lay);
		myLayout.setOnClickListener(new MyOnClickLayout());
		myLayout =(RelativeLayout)findViewById(R.id.restore_fromSD);
		myLayout.setOnClickListener(new MyOnClickLayout());
	}
	class MyOnClickLayout implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.data_backup_lay://数据备份
				AlertDialog.Builder builder = new AlertDialog.Builder(DataBackupSetting.this);
				builder.setTitle("提示")
					.setMessage("软件将把数据导出备份并将替换掉之前的备份文件，点击“确定”继续。")
					.setCancelable(false)
					.setPositiveButton("确定", new MyDialogOnClick())
					.setNegativeButton("取消", new MyDialogOnClick());
				AlertDialog alert=builder.create();
				alert.show();
				break;
			case R.id.restore_fromSD://数据还原
				Intent intent =new Intent();
				intent.setClass(DataBackupSetting.this, DataRestorePreView.class);
				startActivity(intent);
				break;
			}
			
		}
		
	}
	class MyDialogOnClick implements DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch(which){
			case Dialog.BUTTON_POSITIVE://确定
				JsonToSDtxt jsonToS = new JsonToSDtxt(DataBackupSetting.this);
				jsonToS.LetJsonTotxt();
				dialog.dismiss();
				break;
			case Dialog.BUTTON_NEGATIVE://取消
				dialog.dismiss();
				break;
			}
		}
	}
	
}
