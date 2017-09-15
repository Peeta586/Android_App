package com.example.jiemeng;

import com.example.mainsurface.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class JieMengActivity extends Activity {
	private TextView dream_content;
	private ListView dream_list;
	private String[] name_dream = { "Ů����", "��ʦ", "��ĸ", "����", "ҽ��", "����", "С͵",
			"ͬѧ", "ͬ��", "�����ھ�", "С��", "˽��", "����", "���", "����", "����", "�Ⱦ�", "��Ǯ",
			"��ˮ", "����", "ͼ���", "д��", "��ɽ", "����", "����", "����", "����", "���", "��ͻ",
			"�Ĳ�", "ǿ��", "����", "����", "��ֽ", "����", "�ܲ�", "����", "ϴԡ", "�Է�", "�и�",
			"��ɱ", "Ұ��", "����", "����", "����", "ѧУ", "ս��", "�Ҿ�", "����", "����", "��ˮ",
			"����", "��", "����", "��ͼ", "����", "��", "��ʯ", "��ˮ", "����", "ֽ", "����", "Ь",
			"�ɻ�", "Կ��", "����", "�ϻ�", "С����", "�ڹ�", "����", "��", "����", "��ѻ", "����",
			"��", "����", "����", "֩��", "����", "��", "������", "����", "����", "��", "����",
			"��", "����", "��ȸ", "����", "����", "��Ů", "��", "����", "��԰", "��", "ˮ��",
			"��·", "�������", "���", "��", "��", "��", "ˮ��", "ɭ��", "ɽ", "ˮ", "�ʺ�",
			"����", "��", "��", "����", "����", "����", "���", "��", "����", "ѩ��" };

	private Button backBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dream_activity);
		backBtn =(Button)findViewById(R.id.dream_Back);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		InitView();
	}

	private void InitView() {
		dream_content =(TextView)findViewById(R.id.dream_content);
		dream_list = (ListView) findViewById(R.id.dream_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.dream_list_item,//ֻ����һ��������id��TextView
                name_dream);//data�ȿ��������飬Ҳ������List����
		dream_list.setAdapter(adapter);
		dream_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				new AsyTaskJieDream(dream_content).execute(name_dream[position]);
			}
		});
	}

}
