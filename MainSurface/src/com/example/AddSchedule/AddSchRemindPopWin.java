package com.example.AddSchedule;


import kankan.wheel.widget.ArrayWheelAdapter;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
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

import com.example.mainsurface.R;

public class AddSchRemindPopWin {
	private PopupWindow remindPopWin;
	private View popView;
	private Context context;
	/**
	 * wheel控件需要的一些参数
	 */
	private String[] remindStrings =null;
	public AddSchRemindPopWin(Context context){
		this.context =context;
		popView= ((Activity) context).getLayoutInflater().inflate(
				R.layout.addsch_remind_popwin, null);
		/**
		 * 创建PopupWindow对象
		 */
		remindPopWin = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);//高度为三分之一的屏幕高度
		/**
		 * 设置popwindow一些参数：背景，焦点，点击外部会自动消失
		 */
		remindPopWin.setBackgroundDrawable(context.getResources().getDrawable(R.color.schedule_add_popbg));
		remindPopWin.setFocusable(true);
		remindPopWin.setOutsideTouchable(true);
		/**
		 * 设置完成按钮监听器
		 */
		setFinishBtn();
		/**
		 * wheelview视图的一些设置
		 */
		remindStrings = context.getResources()
				.getStringArray(R.array.remind_array);
		InitWheel();
		
	}
	private void setFinishBtn(){
		Button finishtBtn = (Button)popView.findViewById(R.id.todo_finish_remind);
		finishtBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//判断PopupWindow是否还在显示
				if(remindPopWin.isShowing()){
					//关闭PopupWindow窗口
					remindPopWin.dismiss();
				}
			}
		});
	}
	public void openPopWindow(View view){
		remindPopWin.showAtLocation(view, Gravity.BOTTOM, 0, 0);
	}
	private void InitWheel(){
		WheelView wheel = (WheelView)popView.findViewById(R.id.sch_remind_wheel);
		wheel.setAdapter(new ArrayWheelAdapter<String>(remindStrings,
				remindStrings.length));
		wheel.setCurrentItem(0);
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
		TextView textRemind =(TextView)((Activity)context).findViewById(R.id.remindTx);
		textRemind.setText(remindStrings[newvalue]);
	}
}
