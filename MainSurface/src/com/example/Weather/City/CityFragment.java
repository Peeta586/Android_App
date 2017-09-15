package com.example.Weather.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.Weather.cityDB.FileService;
import com.example.mainsurface.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class CityFragment extends Fragment {
	private View view;
	private static String prov_id = "311101";// 初始化为北京
	private ListView listView;
	private List<Map<String, String>> list = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.city_list, container, false);
		// 获取listview对象
		listView = (ListView) view.findViewById(R.id.city_list);
		InitListView();

		return view;
	}

	public void InitListView() {
		list = new ArrayList<Map<String, String>>();
		FileService fileService = new FileService(getActivity());

		SQLiteDatabase db = fileService.saveWeatherDBToSD();
		Cursor cursor = db.rawQuery("select * from City where prov_id ="
				+ prov_id, null);

		int colum = cursor.getColumnCount();
		while (cursor.moveToNext()) {
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < colum; i++) {
				String colo_name = cursor.getColumnName(i);
				String colo_value = cursor.getString(cursor
						.getColumnIndex(colo_name));
				map.put(colo_name, colo_value);
			}
			list.add(map);
		}
		if (db != null) {
			db.close();
		}
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), list,
				R.layout.city_list_item, new String[] { "city_name" },
				new int[] { R.id.city_list_item });

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Map<String, String> map = list.get(position);
				//TabFragmentTian.setCityCode(map.get("city_id"));
				//TabFragmentTian.setCity_name(map.get("city_name"));
				getFinish(map.get("city_id"),map.get("city_name"));
			}
		});
	}

	public static void setProv_id(String p_id) {
		prov_id = p_id;
	}
	private void getFinish(String city_code,String city_name){
		Bundle bundle = new Bundle();
		bundle.putString("city_code", city_code);
		bundle.putString("city_name", city_name);
		Intent mIntent = new Intent();
		mIntent.putExtras(bundle);
		this.getActivity().setResult(Activity.RESULT_OK,mIntent);
		this.getActivity().finish();
	}
}
