package com.example.AddSchedule;

import java.util.Calendar;
import com.example.calDrawView.CalendarVariable;
import com.example.mainsurface.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddSchActivity extends Activity {
	private EditText addText;
	private Button backBtn;
	private Button finishBtn;
	private Bundle bundle = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_schedule_activity);
	//	curDate = new Date(System.currentTimeMillis());
		addText =(EditText)findViewById(R.id.addschedittext);
		Intent intent = this.getIntent();
		bundle = intent.getExtras();
		// times=bundle.getString("dateMonth");//初始化将传入时间加到time中
		/**
		 * 添加Title按钮的监听事件
		 */
		setTitleBtn();
		setLayoutOnClick();
		InitTimes();
	}

	private void InitTimes() {
		TextView tV = (TextView) this.findViewById(R.id.timeTx);
		String[] weeks = new String[] { "", "日", "一", "二", "三", "四", "五", "六" };
		int month = bundle.getInt("month");
		int day = bundle.getInt("day");
		Calendar cal = Calendar.getInstance();
		cal.set(CalendarVariable.getCurrentYear(), month, day);
		String time = String.format("%02d", month + 1) + "月"
				+ String.format("%02d", day) + "日 " + "周"
				+ weeks[cal.get(Calendar.DAY_OF_WEEK)] + " "
				+ String.format("%02d", bundle.getInt("hour")) + ":"
				+ String.format("%02d", bundle.getInt("minute"));
		tV.setText(time);
	}

	private void setTitleBtn() {
		backBtn = (Button) findViewById(R.id.AddSchBack);
		finishBtn = (Button) findViewById(R.id.AddSchfinish);
		backBtn.setOnClickListener(new MyBtnClickForTitle());
		finishBtn.setOnClickListener(new MyBtnClickForTitle());
	}

	private void setLayoutOnClick() {
		RelativeLayout rlLayout = (RelativeLayout) findViewById(R.id.timerl);
		rlLayout.setOnClickListener(new MyLayoutOnClick());
		rlLayout = (RelativeLayout) findViewById(R.id.remindrl);
		rlLayout.setOnClickListener(new MyLayoutOnClick());
		rlLayout = (RelativeLayout) findViewById(R.id.repeatrl);
		rlLayout.setOnClickListener(new MyLayoutOnClick());
	}

	class MyLayoutOnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.timerl:
				new AddSchTimePopWin(AddSchActivity.this, bundle)
						.openPopWindow(AddSchActivity.this.findViewById(R.id.add_schedule_main));
				break;
			case R.id.remindrl:
				new AddSchRemindPopWin(AddSchActivity.this).openPopWindow(AddSchActivity.this.findViewById(R.id.add_schedule_main));
				break;
			case R.id.repeatrl:
				new AddSchRepeatPopWIn(AddSchActivity.this).openPopWindow(AddSchActivity.this.findViewById(R.id.add_schedule_main));
				break;
			}
		}
	}

	class MyBtnClickForTitle implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.AddSchBack:// 返回按钮
				getFinish(Activity.RESULT_CANCELED);
				break;
			case R.id.AddSchfinish:// 完成按钮
				if(addText.getText().toString().equals("")){
					Toast.makeText(AddSchActivity.this, "日程内容不能为空！", Toast.LENGTH_SHORT).show();
				}else{
					ScheduleIn scheIn= new ScheduleIn(AddSchActivity.this);
					scheIn.insert();
					//通过回传参数的方式通知日视图更新或不更新，在日视图的ActivityResult中处理更新与否
					getFinish(Activity.RESULT_OK);
				}
				break;
			}
		}
	}
	/**
	 * 回传数据的模板
	 */
	 private void getFinish(int action){
		  this.setResult(action);
		  this.finish();
	 }

}
