package com.example.AppInternetCon;

import android.util.Log;

/**
 * @author LSHM
 *完成对数据的解析
 */
public class UserTools {
	public UserTools(){
		
	}
	public static String getUser(String jsonString){
		Log.i("user","getuser");
		if(jsonString.equals("true")){
			return "登录正确！";
		}else if(jsonString.equals("false")){
			return "用户名或密码错误！";
		}else{
			return "网络连接错误！";
		}
	}
}
