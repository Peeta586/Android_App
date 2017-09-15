package com.example.Weather;


import com.example.mainsurface.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ZhiShuListViewAdapter extends BaseAdapter{
//	private List<View> list = new ArrayList<View>();
	private Context context;
	private String[] content;
	/**
	 * 生活指数数组
	 */
	private int[] icons={R.drawable.zs_ic_chuanyi,R.drawable.zs_ic_ganmao,R.drawable.zs_ic_yundong,R.drawable.zs_ic_xiche,
			  R.drawable.zs_ic_liangshai,R.drawable.zs_ic_lvyou,R.drawable.zs_ic_yuehui,
			  R.drawable.zs_ic_shushi,R.drawable.zs_ic_wuran,R.drawable.zs_ic_ziwaixian};
	private String[] items = {"穿衣指数","过敏指数","运动指数","洗车指数","晾晒指数","旅游指数","路况指数","舒适度指数","空气污染指数","紫外线指数"};
	
	public ZhiShuListViewAdapter(Context context,String[] content) {
		this.context = context;
		this.content = content;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return icons.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return icons[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v =(View)inflater.inflate(R.layout.weather_zhishu_item, null);
		
		TextView tView =(TextView)v.findViewById(R.id.tv_title);
		tView.setText(items[position]);
		tView = (TextView)v.findViewById(R.id.tv_value);
		if(content[position].split("，")[0].equals("") || content[position].split("，")[0] ==null){
			tView.setText(content[position]);
		}else{
			tView.setText(content[position].split("，")[0]);
		}
		tView =(TextView)v.findViewById(R.id.tv_content);
		tView.setText(content[position]);
		
		ImageView imageView = (ImageView)v.findViewById(R.id.iv_logo);
		imageView.setImageResource(icons[position]);
		return v;
	}

}
