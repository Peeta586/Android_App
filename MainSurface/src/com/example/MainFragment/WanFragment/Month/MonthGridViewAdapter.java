package com.example.MainFragment.WanFragment.Month;

import java.util.List;
import java.util.Map;

import com.example.mainsurface.R;

import android.content.Context;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MonthGridViewAdapter extends BaseAdapter {
	private List<Map<String, String>> list =null;
	private Context context;
	public MonthGridViewAdapter(Context context,List<Map<String, String>> list) {
		this.context =context;
		this.list =list;
	}
	public void setList(List<Map<String, String>> list){
		this.list =list;
	}

	@Override
	public int getCount() {
		return (list ==null)?0:list.size();
	}

	@Override
	public Object getItem(int position) {
		return list ==null ? null:list.get(position);
	}

	@Override
	public long getItemId(int position) {
	
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView ==null){
			holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.month_grid_item, null);
			holder.yangView =(TextView)convertView.findViewById(R.id.item_gre);
			holder.nongView =(TextView)convertView.findViewById(R.id.item_lunar);
			holder.todayimg =(ImageView)convertView.findViewById(R.id.todayImg);
			holder.holidayTV =(TextView)convertView.findViewById(R.id.textView_date);
			Map<String, String>  map = list.get(position);
			if(map !=null){
				holder.yangView.setText(String.valueOf(map.get("yangli")));
				holder.nongView.setText(String.valueOf(map.get("nongli")));
				/**
				 * 是否为周末
				 */
				if(position %7==0 || position %7==6){
					holder.yangView.setTextColor(Color.RED);
					holder.nongView.setTextColor(Color.RED);
				}
				//是否为今天
				if(map.get("istoday").equals("1")){
					holder.todayimg.setVisibility(View.VISIBLE);
				}else{
					holder.todayimg.setVisibility(View.GONE);
				}
				//是否为假期
				if(map.get("isholiday").equals("")){
					holder.holidayTV.setVisibility(View.GONE);
				}else{
					holder.holidayTV.setVisibility(View.VISIBLE);
				}
			}else{
				holder.yangView.setText("");
				holder.nongView.setText("");
				holder.todayimg.setVisibility(View.GONE);
				holder.holidayTV.setVisibility(View.GONE);
			}
			convertView.setTag(holder);
		}else{
			Map<String, String> map = list.get(position);
			holder = (ViewHolder)convertView.getTag();
			if(map !=null){
				holder.yangView.setText(String.valueOf(map.get("yangli")));
				holder.nongView.setText(String.valueOf(map.get("nongli")));
				if(position %7==0 || position %7==6){
					holder.yangView.setTextColor(Color.RED);
					holder.nongView.setTextColor(Color.RED);
				}
				//是否为今天
				if(map.get("istoday").equals("1")){
					holder.todayimg.setVisibility(View.VISIBLE);
				}else{
					holder.todayimg.setVisibility(View.GONE);
				}
				//是否为假期
				if(map.get("isholiday").equals("")){
					holder.holidayTV.setVisibility(View.GONE);
				}else{
					holder.holidayTV.setVisibility(View.VISIBLE);
				}
			}else{
				holder.yangView.setText("");
				holder.nongView.setText("");
				holder.todayimg.setVisibility(View.GONE);
				holder.holidayTV.setVisibility(View.GONE);
			}
		}
		
		return convertView;
	}
	static class ViewHolder{         
		public TextView yangView;     
		public TextView nongView;   
		public ImageView todayimg;
		public TextView holidayTV;
	}
	/* (non-Javadoc)如果getitem空就返回false表示不可点击
	 * @see android.widget.BaseAdapter#isEnabled(int)
	 */
	@Override
	public boolean isEnabled(int position) {
		if(getItem(position) ==null){
			return false;
		}else
			return super.isEnabled(position);
	}
	
/**
 * @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.myday_timeline_item_common2, null);
			holder.nameView =(TextView)convertView.findViewById(R.id.tv_myday_item_common_title);
			holder.timeView =(TextView)convertView.findViewById(R.id.tv_myday_item_common_time);
			Map<String, Object> map = info.get(position);
			holder.nameView.setText(String.valueOf(map.get("name")));
			holder.timeView.setText(String.valueOf(map.get("startTime")));
			convertView.setTag(holder);
		}else {
			Map<String, Object> map = info.get(position);
			holder = (ViewHolder)convertView.getTag();
			holder.nameView.setText(String.valueOf(map.get("name")));
			holder.timeView.setText(String.valueOf(map.get("startTime")));
		}
		return convertView;
	}
	static class ViewHolder{         
		public TextView nameView;     
		public TextView timeView;   
	}
 */
}
