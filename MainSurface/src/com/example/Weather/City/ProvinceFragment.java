package com.example.Weather.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.Weather.cityDB.FileService;
import com.example.mainsurface.R;

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

public class ProvinceFragment extends Fragment {
	private View view;
	private List<Map<String, String>> list =null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.province_list, container, false);
		ListView listView = (ListView) view.findViewById(R.id.province_list);
		/**
		 * 构建listView资源
		 */
		list = new ArrayList<Map<String,String>>();
		//获取weather数据库
		FileService fileService = new FileService(getActivity());

		SQLiteDatabase db= fileService.saveWeatherDBToSD();
		Cursor cursor= db.rawQuery("select * from Province", null);
		//cursor.moveToFirst();
		int colum = cursor.getColumnCount();
		while (cursor.moveToNext()) {
			Map<String,String>  map = new HashMap<String, String>();
			for(int i=0;i<colum;i++){
				String colo_name = cursor.getColumnName(i);
				String colo_value = cursor.getString(cursor
						.getColumnIndex(colo_name));
				map.put(colo_name, colo_value);
			}
			list.add(map);
		}
		if(db != null){
			db.close();
		}
		SimpleAdapter adapter = new SimpleAdapter(this.getActivity(), list,
				R.layout.city_list_item, new String[] { "prov_name" },
				new int[] { R.id.city_list_item });

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Map<String,String> map = list.get(position);
				CityFragment.setProv_id(map.get("prov_id"));
				CityFragment cityFragment =(CityFragment) ProvinceFragment.this.getFragmentManager().findFragmentByTag("city_frag");
				cityFragment.InitListView(); 	
			}
		});
		
		return view;
	}

}
