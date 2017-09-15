package com.example.AddSchedule;

import kankan.wheel.widget.ArrayWheelAdapter;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;

import com.example.mainsurface.R;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class AddSchRepeatPopWIn {
	private PopupWindow repeatePopWin;
	private View popView;
	private Context context;
	/**
	 * wheel�ؼ���Ҫ��һЩ����
	 */
	private String[] repeatStrings = null;

	public AddSchRepeatPopWIn(Context context) {
		this.context = context;
		popView = ((Activity) context).getLayoutInflater().inflate(
				R.layout.addsch_repeat_popwin, null);
		/**
		 * ����PopupWindow����
		 */
		repeatePopWin = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		/**
		 * ����popwindowһЩ���������������㣬����ⲿ���Զ���ʧ
		 */
		repeatePopWin.setBackgroundDrawable(context.getResources().getDrawable(
				R.color.schedule_add_popbg));
		repeatePopWin.setFocusable(true);
		repeatePopWin.setOutsideTouchable(true);
		/**
		 * ������ɰ�ť������
		 */
		setFinishBtn();
		/**
		 * wheelview��һЩ������ʼ��
		 */
		repeatStrings = context.getResources().getStringArray(
				R.array.repeat_array);
		InitWheel();
	}

	private void setFinishBtn() {
		Button finishtBtn = (Button) popView
				.findViewById(R.id.todo_finish_repeat);
		finishtBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �ж�PopupWindow�Ƿ�����ʾ
				if (repeatePopWin.isShowing()) {
					// �ر�PopupWindow����
					repeatePopWin.dismiss();
				}
			}
		});
	}

	public void openPopWindow(View view) {
		repeatePopWin.showAtLocation(
				view,
				Gravity.BOTTOM, 0, 0);
	}
	private void InitWheel(){
		WheelView wheel = (WheelView)popView.findViewById(R.id.sch_repeat_wheel);
		wheel.setAdapter(new ArrayWheelAdapter<String>(repeatStrings,
				repeatStrings.length));
		wheel.setCurrentItem(0,true);
		wheel.setVisibleItems(3);
		wheel.addChangingListener(changedListener);
		wheel.addScrollingListener(scrollListener);
		wheel.setCyclic(true);
		wheel.setInterpolator(new AnticipateOvershootInterpolator());
	}
	private boolean wheelScrolled = false;
	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
		@Override
		public void onScrollingStarted(WheelView wheel) {
			wheelScrolled = true;
			
		}
		@Override
		public void onScrollingFinished(WheelView wheel) {
			wheelScrolled = false;
			
		}
	};
	private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
		
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			if(!wheelScrolled){
				updateValue(newValue);
			}else{
				updateValue(newValue);
			}
		}
	};
	private void updateValue(int newvalue){
		TextView textRemind =(TextView)((Activity)context).findViewById(R.id.repeatTx);
		textRemind.setText(repeatStrings[newvalue]);
	}
}
