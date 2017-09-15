package com.example.Constellation;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import com.example.AppInternetCon.InternetUser;
import com.example.mainsurface.R;
//import com.example.mainsurface.R;

import android.os.AsyncTask;
import android.view.View;
//import android.widget.TextView;

public class AsyTaskConsFromInternet extends
		AsyncTask<String, Object, List<HashMap<String, String>>> {
	private View parentView;
	private View view;
	private int num;//第几个tab
	private ContentSHowClass coSHowClass =null;
	private WeekShowClass weekShowClass =null;
	private final int NUM =3;
	/**
	 * @param view:为ScrollableViewGroup
	 * @param num
	 */
	public AsyTaskConsFromInternet(View view,int number) {
		//获得子view
		this.parentView =view;
		this.view = ((ScrollableViewGroup)view).getChildAt(number);
		//this.num =num;
		
	}
	private void getNum(){
		switch(parentView.getId()){
		case R.id.today_fliper:
			num = 1;
			break;
		case R.id.tommo_fliper:
			num = 2;
			break;
		case R.id.week_fliper:
			num = 3;
			break;
		}
	}
	@Override
	protected void onPreExecute() {
		getNum();//获得是哪个viewgroup
		if(num !=NUM)
			coSHowClass =new ContentSHowClass(view);
		else
			weekShowClass =new WeekShowClass(view);
			
	}

	@Override
	protected List<HashMap<String, String>> doInBackground(String... params) {
		String path = "http://api.uihoo.com/astro/astro.http.php?fun="
				+ params[0] + "&id=" + params[1] + "&format=xml";
		InputStream inputStream = InternetUser.getJsonContentfromInt(path,
				10000);
		List<HashMap<String, String>> list = null;
		try {
			list = SaxService.readXML(inputStream, "item");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	protected void onPostExecute(List<HashMap<String, String>> result) {

		if(result !=null){
			if(num !=NUM)
				coSHowClass.showContentToPh(result);
			else 
				weekShowClass.ShowContentToPh(result);
		}
	}
}
