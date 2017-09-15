package com.example.jiemeng;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import com.example.AppInternetCon.InternetUser;
import com.example.Constellation.SaxService;

import android.os.AsyncTask;
import android.widget.TextView;

public class AsyTaskJieDream extends AsyncTask<String, Object, List<HashMap<String,String>>> {
	private TextView dream_content;
	public AsyTaskJieDream(TextView view) {
		this.dream_content =view;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected List<HashMap<String, String>> doInBackground(String... params) {
		String path = "http://api.uihoo.com/dream/dream.http.php?key="+params[0]+"&format=xml";
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
		StringBuilder builder =new StringBuilder();
		if(result !=null){
			for(HashMap<String, String> map: result){
				builder.append("***"+map.get("item") +"\n\n");
			}
		}
		dream_content.setText(builder.toString());
	}

}
