package com.example.Constellation;

import java.util.HashMap;
import java.util.List;
//import java.util.Map;

import com.example.mainsurface.R;

//import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContentSHowClass {
	// private Context context;
	private View view;
	private TextView cons_time;
	private TextView supei;
	private TextView lucknum;
	private TextView luckcolor;
	private LinearLayout songheLayout;
	private LinearLayout loveLayout;
	private LinearLayout jobLayout;
	private TextView all_doc;
	
	private final int NUM=5;
	public ContentSHowClass(View view) {
		// this.context =context;
		this.view = view;
	}

	private void InitKong() {
		cons_time = (TextView) view.findViewById(R.id.cons_time);
//		Calendar cal = Calendar.getInstance();
//		String timeStr = cal.get(Calendar.YEAR) + "年" + cal.get(Calendar.MONTH)
//				+ 1 + "月" + cal.get(Calendar.DAY_OF_MONTH) + "日 星期"
//				+ CalendarTools.weekname[cal.get(Calendar.DAY_OF_WEEK) - 1];
//		cons_time.setText(timeStr);
		
		supei =(TextView)view.findViewById(R.id.luck_astro);
		lucknum =(TextView)view.findViewById(R.id.luck_num);
		luckcolor =(TextView)view.findViewById(R.id.luck_color);
		songheLayout =(LinearLayout)view.findViewById(R.id.all_star);
		loveLayout =(LinearLayout)view.findViewById(R.id.love_star);
		jobLayout =(LinearLayout)view.findViewById(R.id.work_star);
		all_doc =(TextView)view.findViewById(R.id.all_doc);
	}
	private void setLayoutStar(LinearLayout starLayout,int num){
		for(int i=1;i<=NUM;i++){
			ImageView image = (ImageView)starLayout.getChildAt(i);
			if(i<num){
				image.setImageResource(R.drawable.star_bright);
			}else{
				image.setImageResource(R.drawable.star_gray);
			}
		}
	}
	public void showContentToPh(List<HashMap<String, String>> list){
		InitKong();//先初始化控件
		//
		cons_time.setText(list.get(11).get("item"));
		supei.setText(list.get(8).get("value"));
		lucknum.setText(list.get(7).get("value"));
		luckcolor.setText(list.get(6).get("value"));
		all_doc.setText(list.get(9).get("value"));
		setLayoutStar(songheLayout,Integer.parseInt(list.get(0).get("rank")));
		setLayoutStar(loveLayout,Integer.parseInt(list.get(1).get("rank")));
		setLayoutStar(jobLayout,Integer.parseInt(list.get(2).get("rank")));
	}
}
