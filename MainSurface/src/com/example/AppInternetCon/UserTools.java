package com.example.AppInternetCon;

import android.util.Log;

/**
 * @author LSHM
 *��ɶ����ݵĽ���
 */
public class UserTools {
	public UserTools(){
		
	}
	public static String getUser(String jsonString){
		Log.i("user","getuser");
		if(jsonString.equals("true")){
			return "��¼��ȷ��";
		}else if(jsonString.equals("false")){
			return "�û������������";
		}else{
			return "�������Ӵ���";
		}
	}
}
