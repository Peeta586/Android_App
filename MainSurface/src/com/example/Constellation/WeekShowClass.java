package com.example.Constellation;

import java.util.HashMap;
import java.util.List;

import com.example.mainsurface.R;

import android.provider.SyncStateContract.Helpers;
import android.view.View;
import android.widget.TextView;

public class WeekShowClass {
	private View view;
	private TextView cons_time1;//���ܿ�ʼ����
	private TextView cons_time2;//���ܽ�������
	private TextView lucktime;//������
	private TextView unlucktime;//��÷��
	private TextView zhengti;//��������
	private TextView have;//�ж���
	private TextView unhave;//�ж���
	private TextView health;//��������
	private TextView workschool;//����ѧҵ����
	private TextView xingyu;
	private TextView littletishi;//С��ʾ
	
	public WeekShowClass(View view) {
		this.view =view;
	}
	private void InitKong(){
		cons_time1 =(TextView)view.findViewById(R.id.cons_time1);
		cons_time2 =(TextView)view.findViewById(R.id.cons_time2);
		lucktime =(TextView)view.findViewById(R.id.luck_day);
		unlucktime =(TextView)view.findViewById(R.id.unluck_day);
		zhengti =(TextView)view.findViewById(R.id.all_doc);
		//loveyunshi =(TextView)view.findViewById(R.id.love_doc);
		have =(TextView)view.findViewById(R.id.have);
		unhave=(TextView)view.findViewById(R.id.unhave);
		health =(TextView)view.findViewById(R.id.health);
		workschool=(TextView)view.findViewById(R.id.work_doc);
		xingyu =(TextView)view.findViewById(R.id.xingyu);
		littletishi =(TextView)view.findViewById(R.id.tishi_doc);
	}
	public void ShowContentToPh(List<HashMap<String, String>> list){
		InitKong();
		cons_time1.setText(list.get(14).get("item"));
		cons_time2.setText(list.get(15).get("item"));
		lucktime.setText(list.get(10).get("value"));
		unlucktime.setText(list.get(11).get("value"));
		zhengti.setText(list.get(0).get("value"));
		have.setText(list.get(5).get("item"));
		unhave.setText(list.get(6).get("item"));
		health.setText(list.get(7).get("value"));
		workschool.setText(list.get(8).get("value"));
		xingyu.setText(list.get(9).get("value"));
		littletishi.setText(list.get(12).get("value"));
	}
}
