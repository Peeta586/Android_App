package com.example.AddSchedule;

import java.util.List;
import java.util.Map;

import com.example.mainsurface.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScheduleListViewAdapter extends BaseAdapter {
	public  List<Map<String,Object>> info = null;
	private Context context;
//	private List<View> rowViews=new ArrayList<View>();
	public ScheduleListViewAdapter(List<Map<String, Object>> list ,Context context) {
		this.context = context;
		info =list;
	}
	public void refreshInfo(List<Map<String,Object>> infos){
		this.info =infos;
	}
	@Override
	public int getCount() {
		return info == null ? 0:info.size();
	}
	@Override
	public Object getItem(int position) {
		Map<String, Object> map=info.get(position);
		return (map == null)? 0:map.get("id");
	}
	@Override
	public long getItemId(int position) {
		Map<String, Object> map=info.get(position);
		return Long.parseLong(String.valueOf((map == null) ? 0:map.get("id")));
	}

	@Override
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

}
