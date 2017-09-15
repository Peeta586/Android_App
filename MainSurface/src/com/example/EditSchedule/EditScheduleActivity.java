package com.example.EditSchedule;

import com.example.AddSchedule.AddSchRemindPopWin;
import com.example.AddSchedule.AddSchRepeatPopWIn;
import com.example.AddSchedule.AddSchTimePopWin;
import com.example.AddSchedule.ScheduleIn;
import com.example.DataBase.MyTodoScheduleDB;
import com.example.mainsurface.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author LSHM
 *待解决的问题就是将wheel的显示与textview中的值对应
 */
public class EditScheduleActivity extends Activity {
	private String id;
	private MyTodoScheduleDB helper = null;
	private EditText nameText;
	private TextView tvTime;
	private TextView tvRemind;
	private TextView tvRepeat;
	private Bundle bundle;
	private Button editBtn;
	private Button finishBtn;
	private RelativeLayout timeLayout;
	private RelativeLayout remindLayout;
	private RelativeLayout repeatLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_edit_shedule);
		Intent intent = this.getIntent();
		id = intent.getStringExtra("id");
		helper = new MyTodoScheduleDB(this);
		InitKongjian();
		getScheduleById();
		setLayoutOnClick();
	}

	private void InitKongjian() {
		nameText = (EditText) findViewById(R.id.addschedittext);
		tvTime = (TextView) findViewById(R.id.timeTx);
		tvRemind = (TextView) findViewById(R.id.remindTx);
		tvRepeat = (TextView) findViewById(R.id.repeatTx);

		Button backBtn = (Button) findViewById(R.id.sch_edit_back);
		editBtn = (Button) findViewById(R.id.sch_edit);
		finishBtn = (Button) findViewById(R.id.sch_edit_finish);
		TextView delete = (TextView) findViewById(R.id.deleteTV);
		backBtn.setOnClickListener(new MyBtnOnClick());
		editBtn.setOnClickListener(new MyBtnOnClick());
		finishBtn.setOnClickListener(new MyBtnOnClick());
		delete.setOnClickListener(new MyBtnOnClick());
	}

	/**
	 * 功能：1）通过id获得日程列表，并将这些信息放入到相应textview中 2）并且完成对bundle的封装
	 */
	private void getScheduleById() {
		bundle = new Bundle();
		String[] weekname = new String[] { "", "周日 ", "周一 ", "周二 ", "周三 ",
				"周四 ", "周五 ", "周六 " };
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from Schedule where id =" + id,
				null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("name"));
			nameText.setText(name);
			String times = cursor.getString(cursor.getColumnIndex("startDate"));
			String[] array = times.split("-");// array长度为3数组0：年，1月，2日
			/**
			 * 对bundle 封装
			 */
			bundle.putInt("month", Integer.parseInt(array[1]) - 1);// 注意月份要减一
			bundle.putInt("day", Integer.parseInt(array[2]));
			// *****************
			String tvTimeText = array[1] + "月" + array[2] + "日 ";
			tvTimeText += weekname[Integer.parseInt(cursor.getString(cursor
					.getColumnIndex("repeat_week")))];
			String startTime = cursor.getString(cursor
					.getColumnIndex("startTime"));
			tvTimeText += startTime;
			/**
			 * 对bundle 封装
			 */
			bundle.putInt("hour", Integer.parseInt(startTime.split(":")[0]));
			bundle.putInt("minute", Integer.parseInt(startTime.split(":")[1]));
			// *******************
			tvTime.setText(tvTimeText);
			tvRemind.setText(getRemindBycontent(cursor.getString(cursor
					.getColumnIndex("remind"))));
			tvRepeat.setText(getRepeatBycontent(cursor,
					cursor.getString(cursor.getColumnIndex("repeat_type"))));
		}
	}

	private String getRemindBycontent(String content) {
		if (content.equals("-1")) {
			return "不提醒";
		} else if (content.equals("0")) {
			return "正点提醒";
		} else if (content.equals("5")) {
			return "提前5分钟";
		} else if (content.equals("10")) {
			return "提前10分钟";
		} else if (content.equals("15")) {
			return "提前15分钟";
		} else if (content.equals("30")) {
			return "提前30分钟";
		} else if (content.equals("60")) {
			return "提前1小时";
		} else if (content.equals("120")) {
			return "提前2小时";
		} else if (content.equals("180")) {
			return "提前3小时";
		} else if (content.equals("1440")) {
			return "提前1天";
		} else if (content.equals("2880")) {
			return "提前2天";
		} else if (content.equals("4320")) {
			return "提前3天";
		}
		return "";
	}

	private String getRepeatBycontent(Cursor cursor, String content) {
		if (content.equals("0")) {
			return "不重复";
		} else if (content.equals("1")) {
			return "每天";
		} else if (content.equals("2")) {
			String number = cursor.getString(cursor
					.getColumnIndex("repeat_number"));
			if (number.equals("1")) {
				return "每周";
			} else if (number.equals("2")) {
				return "每两周";
			}
		} else if (content.equals("3")) {
			String number = cursor.getString(cursor
					.getColumnIndex("repeat_number"));
			if (number.equals("1")) {
				return "每月";
			} else if (number.equals("2")) {
				return "两月";
			} else if (number.equals("3")) {
				return "每三月";
			}
		} else if (content.equals("4")) {
			return "每年";
		}
		return "";
	}

	class MyBtnOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.sch_edit_back:// 返回
				getFinish("back");
				break;
			case R.id.sch_edit:// 编辑
				getChange();
				break;
			case R.id.sch_edit_finish:// 完成
				ScheduleIn in = new ScheduleIn(EditScheduleActivity.this);
				in.update(id);
				getFinish("finish");
				Toast.makeText(getApplicationContext(), "修改成功!",
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.deleteTV:// 删除日程
				// new DialogForDelete(EditScheduleActivity.this);
				new DialogForDelete(EditScheduleActivity.this, id).showDialog();
				break;
			}

		}
	}

	/**
	 * 将编辑按钮换成完成并且修改Edittext属性
	 */
	private void getChange() {
		/**
		 * 改变控件属性，使他们可编辑
		 */
		editBtn.setVisibility(View.GONE);
		finishBtn.setVisibility(View.VISIBLE);
		timeLayout.setClickable(true);
		remindLayout.setClickable(true);
		repeatLayout.setClickable(true);
		nameText.setEnabled(true);
		float scale;
		// 像素px与sp之间转化的比率
		scale = this.getResources().getDisplayMetrics().density;
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, (int) (150 * scale));
		params.addRule(RelativeLayout.RIGHT_OF, R.id.schcb);
		nameText.setLayoutParams(params);
		nameText.setSelection(nameText.getText().toString().length());
	}

	public void getFinish(String action) {
		Intent intent = new Intent();
		intent.putExtra("action", action);
		this.setResult(Activity.RESULT_OK, intent);
		this.finish();

	}

	private void setLayoutOnClick() {
		timeLayout = (RelativeLayout) findViewById(R.id.timerl);
		timeLayout.setOnClickListener(new MyLayoutOnClick());
		remindLayout = (RelativeLayout) findViewById(R.id.remindrl);
		remindLayout.setOnClickListener(new MyLayoutOnClick());
		repeatLayout = (RelativeLayout) findViewById(R.id.repeatrl);
		repeatLayout.setOnClickListener(new MyLayoutOnClick());
		timeLayout.setClickable(false);
		remindLayout.setClickable(false);
		repeatLayout.setClickable(false);
	}

	class MyLayoutOnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.timerl:// 在oncreate中的getScheduleById()函数已经对bundle封装
				new AddSchTimePopWin(EditScheduleActivity.this, bundle)
						.openPopWindow(EditScheduleActivity.this
								.findViewById(R.id.edit_sch_mainlayout));
				break;
			case R.id.remindrl:
				new AddSchRemindPopWin(EditScheduleActivity.this)
						.openPopWindow(EditScheduleActivity.this
								.findViewById(R.id.edit_sch_mainlayout));
				break;
			case R.id.repeatrl:
				new AddSchRepeatPopWIn(EditScheduleActivity.this)
						.openPopWindow(EditScheduleActivity.this
								.findViewById(R.id.edit_sch_mainlayout));
				break;
			}
		}
	}
}
