package com.example.Alarm;

import com.example.mainsurface.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class RemindAlarmShow extends Activity {
	private ImageView arrow_anim;
	private AnimationDrawable anim;
	private TextView delay;
	private TextView remindTime;
	private TextView title;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_remind_dailogfg);
		intent = this.getIntent();// 先获得意图
		arrow_anim = (ImageView) findViewById(R.id.getup_arrow);
		anim = (AnimationDrawable) arrow_anim.getBackground();
		anim.start();
		IniteKong();
	}

	private void IniteKong() {
		delay = (TextView) findViewById(R.id.tv_snoozeArea);
		remindTime = (TextView) findViewById(R.id.tv_snoozeOther_alarmRemind_time);
		title = (TextView) findViewById(R.id.tv_snoozeOther_alarmRemind_title);
		remindTime.setText(intent.getStringExtra("time"));
		title.setText(intent.getStringExtra("name"));
		delay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				finish();
			}
		});
	}

}
