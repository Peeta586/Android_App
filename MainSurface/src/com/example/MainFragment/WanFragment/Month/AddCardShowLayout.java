package com.example.MainFragment.WanFragment.Month;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.Alarm.RemindAlarmShow;
import com.example.mainsurface.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;

public class AddCardShowLayout {
	private Context context;
	private Fragment fragment;
	private int[] student_icon = { R.drawable.icon_novel,
			R.drawable.mark_icon_bell };
	private String[] student_name = { "�����Ƽ�", "����֪ͨ" };
	private int[] life_icon = { R.drawable.tieshi, R.drawable.flagreen };
	private String[] life_name = { "������ʿ", "�ճ�����" };
	private PopupWindow popupWindow;
	private GridView student_GridView;
	private GridView life_GridView;

	public AddCardShowLayout(Context context, Fragment fragment) {
		this.context = context;
		this.fragment = fragment;
		View popView = ((Activity) context).getLayoutInflater().inflate(
				R.layout.add_card, null);
		/**
		 * ����PopupWindow����
		 */
		popupWindow = new PopupWindow(popView, 400,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		/**
		 * ΪPopupWindow���ñ������ڵ���PopupWindow����ĵط�ʱ�� PopupWindow���˳���ǰ̨
		 */
		popupWindow.setBackgroundDrawable(this.context.getResources().getDrawable(R.drawable.bg_2));
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		
		student_GridView = (GridView) popView.findViewById(R.id.student_qu);
		life_GridView = (GridView) popView.findViewById(R.id.life_service);
		// ���ü�����
		student_GridView.setOnItemClickListener(new ItemClickListener());
		life_GridView.setOnItemClickListener(new ItemClickListener());
	}

	private final class ItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (parent.getId()) {
			case R.id.student_qu:
				// �ж�PopupWindow�Ƿ�����ʾ
				if (popupWindow.isShowing()) {
					// �ر�PopupWindow����
					popupWindow.dismiss();
					if(position ==0){
						AddCard addCard =new AddCard(context, fragment.getView());
						LayoutInflater inflater =LayoutInflater.from(context);
						LinearLayout layout =(LinearLayout)inflater.inflate(R.layout.tieshi_layout, null);
						addCard.AddView(layout);
					}
				}
				break;
			case R.id.life_service:
				// �ж�PopupWindow�Ƿ�����ʾ
				if (popupWindow.isShowing()) {
					// �ر�PopupWindow����
					
					popupWindow.dismiss();
				}
				break;
			}

		}
	}

	public void openPopWindow() {
		popupWindow.showAtLocation(
				((Activity) context).findViewById(R.id.mainlayout),
				Gravity.LEFT, 0, 0);
		student_GridView.setAdapter(stud_Adapter());
		life_GridView.setAdapter(life_Adapter());
	}

	/**
	 * ѧ������
	 */
	private ListAdapter stud_Adapter() {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < student_icon.length; i++) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("icon_add", student_icon[i]);
			item.put("title_add", student_name[i]);
			data.add(item);
		}
		SimpleAdapter adapter = new SimpleAdapter(this.context, data,
				R.layout.grid_item, new String[] { "icon_add", "title_add" },
				new int[] { R.id.icon_add, R.id.title_add });

		return adapter;
	}

	/**
	 * ��������
	 */
	private ListAdapter life_Adapter() {

		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < life_icon.length; i++) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("icon_add", life_icon[i]);
			item.put("title_add", life_name[i]);
			data.add(item);
		}
		SimpleAdapter adapter = new SimpleAdapter(this.context, data,
				R.layout.grid_item, new String[] { "icon_add", "title_add" },
				new int[] { R.id.icon_add, R.id.title_add });
		return adapter;
	}
}
