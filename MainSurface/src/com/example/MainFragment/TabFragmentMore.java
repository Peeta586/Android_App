package com.example.MainFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ApplicationSetting.ApplicationSet;
import com.example.Calculate.CalculateActivity;
import com.example.Constellation.ConstellationActivity;
import com.example.LoginRegiste.LoginRegisteActivity;
import com.example.ThemeSetting.ThemeSettingActivity;
import com.example.WomanMC.WomanMCActivity;
import com.example.jiemeng.JieMengActivity;
import com.example.mainsurface.MainActivity;
import com.example.mainsurface.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class TabFragmentMore extends Fragment implements OnItemClickListener,
		OnItemSelectedListener {
	private View view;
	private GridView settingGridView;
	private int[] ResID = new int[] { R.drawable.fun_ic_shezhi,
			R.drawable.fun_ic_user, R.drawable.fun_ic_zhuti,
			R.drawable.fun_ic_jiemeng, R.drawable.fun_ic_xingzuo,
			R.drawable.fun_ic_shengli, R.drawable.fun_ic_jisuan,
			R.drawable.fun_ic_search, R.drawable.fun_ic_pingfen,
			R.drawable.fun_ic_like, R.drawable.fun_ic_feedback };
	private String[] cell_title = new String[] { "系统设置", "个人中心", "主题皮肤",
			"周公解梦", "星座提醒", "生理规律", "日期计算", "搜索", "推荐我们", "好评", "意见反馈" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.more_activity, container, false);
		InitGridView();
		return view;
	}

	private void InitGridView() {
		settingGridView = (GridView) view.findViewById(R.id.setting_gridview);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < ResID.length; i++) {
			Map<String, Object> cell = new HashMap<String, Object>();
			cell.put("grid_img_more", ResID[i]);
			cell.put("grid_tv_more", cell_title[i]);
			list.add(cell);
		}

		SimpleAdapter adapter = new SimpleAdapter(this.getActivity(), list,
				R.layout.more_grid_item, new String[] { "grid_img_more",
						"grid_tv_more" }, new int[] { R.id.grid_img_more,
						R.id.grid_tv_more });
		settingGridView.setAdapter(adapter);
		settingGridView.setOnItemClickListener(this);
		settingGridView.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent();
		switch (position) {
		case 0:// 系统设置
			intent.setClass(this.getActivity(), ApplicationSet.class);
			startActivity(intent);
			break;
		case 1:// 个人中心
			intent.setClass(this.getActivity(), LoginRegisteActivity.class);
			// TabFragmentWan.this.getActivity().startActivity(intent);
			startActivityForResult(intent, MainActivity.getRequestCodeWan());
			break;
		case 2:// 主题皮肤
			intent.setClass(this.getActivity(), ThemeSettingActivity.class);
			startActivity(intent);
			break;
		case 3:// 周公解梦
			intent.setClass(this.getActivity(), JieMengActivity.class);
			startActivity(intent);
			break;
		case 4:// 星座提醒
			intent.setClass(getActivity(), ConstellationActivity.class);
			startActivity(intent);
			break;
		case 5:// 生理规律
			intent.setClass(getActivity(), WomanMCActivity.class);
			startActivity(intent);
			break;
		case 6:// 日期计算
			intent.setClass(getActivity(), CalculateActivity.class);
			startActivity(intent);
			break;
		case 7:// 搜索
			break;
		case 8:// 推荐我们
			break;
		case 9:// 好评
			break;
		case 10:// 意见反馈
			break;
		}

	}

}
