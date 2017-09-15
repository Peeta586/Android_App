package com.example.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RemindReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("nao", "BroadcastReceiver");
		String name =intent.getStringExtra("name");
		String time = intent.getStringExtra("time");
		int id =intent.getIntExtra("id", 0);
		Intent intent2 = new Intent();
		intent2.setClass(context, RemindAlarmShow.class);
		intent2.putExtra("name", name);
		intent2.putExtra("time", time);
		intent2.putExtra("id", id);
		intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//µ÷ÓÃ
		context.startActivity(intent2);	
	}

}
