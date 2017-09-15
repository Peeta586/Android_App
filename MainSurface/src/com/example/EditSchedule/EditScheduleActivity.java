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
 *�������������ǽ�wheel����ʾ��textview�е�ֵ��Ӧ
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
	 * ���ܣ�1��ͨ��id����ճ��б�������Щ��Ϣ���뵽��Ӧtextview�� 2��������ɶ�bundle�ķ�װ
	 */
	private void getScheduleById() {
		bundle = new Bundle();
		String[] weekname = new String[] { "", "���� ", "��һ ", "�ܶ� ", "���� ",
				"���� ", "���� ", "���� " };
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from Schedule where id =" + id,
				null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("name"));
			nameText.setText(name);
			String times = cursor.getString(cursor.getColumnIndex("startDate"));
			String[] array = times.split("-");// array����Ϊ3����0���꣬1�£�2��
			/**
			 * ��bundle ��װ
			 */
			bundle.putInt("month", Integer.parseInt(array[1]) - 1);// ע���·�Ҫ��һ
			bundle.putInt("day", Integer.parseInt(array[2]));
			// *****************
			String tvTimeText = array[1] + "��" + array[2] + "�� ";
			tvTimeText += weekname[Integer.parseInt(cursor.getString(cursor
					.getColumnIndex("repeat_week")))];
			String startTime = cursor.getString(cursor
					.getColumnIndex("startTime"));
			tvTimeText += startTime;
			/**
			 * ��bundle ��װ
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
			return "������";
		} else if (content.equals("0")) {
			return "��������";
		} else if (content.equals("5")) {
			return "��ǰ5����";
		} else if (content.equals("10")) {
			return "��ǰ10����";
		} else if (content.equals("15")) {
			return "��ǰ15����";
		} else if (content.equals("30")) {
			return "��ǰ30����";
		} else if (content.equals("60")) {
			return "��ǰ1Сʱ";
		} else if (content.equals("120")) {
			return "��ǰ2Сʱ";
		} else if (content.equals("180")) {
			return "��ǰ3Сʱ";
		} else if (content.equals("1440")) {
			return "��ǰ1��";
		} else if (content.equals("2880")) {
			return "��ǰ2��";
		} else if (content.equals("4320")) {
			return "��ǰ3��";
		}
		return "";
	}

	private String getRepeatBycontent(Cursor cursor, String content) {
		if (content.equals("0")) {
			return "���ظ�";
		} else if (content.equals("1")) {
			return "ÿ��";
		} else if (content.equals("2")) {
			String number = cursor.getString(cursor
					.getColumnIndex("repeat_number"));
			if (number.equals("1")) {
				return "ÿ��";
			} else if (number.equals("2")) {
				return "ÿ����";
			}
		} else if (content.equals("3")) {
			String number = cursor.getString(cursor
					.getColumnIndex("repeat_number"));
			if (number.equals("1")) {
				return "ÿ��";
			} else if (number.equals("2")) {
				return "����";
			} else if (number.equals("3")) {
				return "ÿ����";
			}
		} else if (content.equals("4")) {
			return "ÿ��";
		}
		return "";
	}

	class MyBtnOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.sch_edit_back:// ����
				getFinish("back");
				break;
			case R.id.sch_edit:// �༭
				getChange();
				break;
			case R.id.sch_edit_finish:// ���
				ScheduleIn in = new ScheduleIn(EditScheduleActivity.this);
				in.update(id);
				getFinish("finish");
				Toast.makeText(getApplicationContext(), "�޸ĳɹ�!",
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.deleteTV:// ɾ���ճ�
				// new DialogForDelete(EditScheduleActivity.this);
				new DialogForDelete(EditScheduleActivity.this, id).showDialog();
				break;
			}

		}
	}

	/**
	 * ���༭��ť������ɲ����޸�Edittext����
	 */
	private void getChange() {
		/**
		 * �ı�ؼ����ԣ�ʹ���ǿɱ༭
		 */
		editBtn.setVisibility(View.GONE);
		finishBtn.setVisibility(View.VISIBLE);
		timeLayout.setClickable(true);
		remindLayout.setClickable(true);
		repeatLayout.setClickable(true);
		nameText.setEnabled(true);
		float scale;
		// ����px��sp֮��ת���ı���
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
			case R.id.timerl:// ��oncreate�е�getScheduleById()�����Ѿ���bundle��װ
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
