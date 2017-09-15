package com.example.ThemeSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.mainsurface.MainActivity;
import com.example.mainsurface.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ThemeSettingActivity extends Activity {
	private GridView gridView;
	// private LinearLayout layout_loading;
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private String[] theme_title = { "蓝光波纹", "细微渐变", "清新绿色", "迷人太空", "混色插画" };

	private Button btnBack;
	private int prePosition = -1;

	private SharedPreferences shPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_themesetting);
		shPref = this.getSharedPreferences("themeSet", Context.MODE_PRIVATE);
		Init();
		/**
		 * 返回
		 */
		btnBack = (Button) findViewById(R.id.btn_back);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void Init() {
		gridView = (GridView) findViewById(R.id.grid_theme);
		// layout_loading = (LinearLayout)
		// findViewById(R.id.layout_loading);从网络下载主题图片时用
		getList();
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.theme_grid_item, new String[] { "title", "img" },
				new int[] { R.id.theme_title, R.id.image_theme });
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new MyItemClick());
	}

	private void getList() {
		for (int i = 0; i < MainActivity.theme_img.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", theme_title[i]);
			map.put("img", MainActivity.theme_img[i]);
			list.add(map);
		}
	}

	class MyItemClick implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (position != prePosition) {
				if (prePosition != -1) {// 将以前的选项背景恢复
					ImageView image = (ImageView) parent
							.getChildAt(prePosition).findViewById(
									R.id.iv_usedMark);
					image.setSelected(false);
				}
				ImageView img = (ImageView) view.findViewById(R.id.iv_usedMark);
				img.setSelected(true);
				setThemeforApp(position);
				prePosition = position;
			}
		}
	}

	private void setThemeforApp(int position) {
		SharedPreferences.Editor editor = shPref.edit();
		editor.putInt("position", position);
		if (editor.commit()) {
			Toast.makeText(this, "主题设置成功!", Toast.LENGTH_SHORT).show();
		}
	}
}
