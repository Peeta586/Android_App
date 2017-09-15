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
	private String[] name_dream = { "女朋友", "老师", "父母", "朋友", "医生", "警察", "小偷",
			"同学", "同事", "亲戚邻居", "小孩", "私奔", "接吻", "表白", "恋人", "发誓", "喝酒", "金钱",
			"喝水", "犯罪", "图书馆", "写作", "爬山", "出国", "比赛", "赞扬", "婚礼", "庆典", "冲突",
			"赌博", "强奸", "厕所", "天堂", "报纸", "唱歌", "跑步", "跳舞", "洗浴", "吃饭", "孕妇",
			"自杀", "野餐", "诬陷", "饥饿", "旅行", "学校", "战争", "家具", "工具", "谜语", "溺水",
			"袜子", "剑", "被子", "地图", "衬衣", "笔", "钻石", "香水", "剪刀", "纸", "吉他", "鞋",
			"飞机", "钥匙", "梯子", "老虎", "小动物", "乌龟", "昆虫", "狼", "老鼠", "乌鸦", "蚂蚁",
			"草", "猴子", "鸽子", "蜘蛛", "家畜", "狗", "爬行类", "鲨鱼", "松鼠", "蛇", "青蛙",
			"花", "蚊子", "麻雀", "死人", "地狱", "仙女", "桥", "寺庙", "公园", "塔", "水池",
			"铁路", "城市乡村", "田地", "雨", "冰", "云", "水灾", "森林", "山", "水", "彩虹",
			"月亮", "火", "风", "泥土", "星星", "闪电", "天空", "海", "悬崖", "雪地" };

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
                R.layout.dream_list_item,//只能有一个定义了id的TextView
                name_dream);//data既可以是数组，也可以是List集合
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
