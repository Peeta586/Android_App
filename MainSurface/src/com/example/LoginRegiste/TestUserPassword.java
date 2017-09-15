package com.example.LoginRegiste;

import com.example.AppInternetCon.UserTools;
import com.example.AppInternetCon.InternetUser;

import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author LSHM
 *三个类型参数的说明
 *1.为doInbackground函数的类型
 *2.onProgressUpdate的参数类型
 *3.doInbackground返回参数的类型，和onPostExecute的参数类型
 */
public class TestUserPassword extends AsyncTask<Integer, Integer, String> {
	private TextView testView;
	private String path;
	private LinearLayout linearLayout;
	public TestUserPassword(TextView textView,LinearLayout linear, String path) {
		this.testView = textView;
		this.linearLayout = linear;
		this.path = path;
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		testView.setText("开始验证用户信息！");
		linearLayout.setVisibility(View.VISIBLE);
	}
	@Override
	protected String doInBackground(Integer... params) {
		String jsonString =InternetUser.getJsonContent(path,params[0]);
		return jsonString;
	}
	@Override
	protected void onPostExecute(String result) {
		testView.setText(UserTools.getUser(result));
		linearLayout.setVisibility(View.INVISIBLE);
	}
	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

}
