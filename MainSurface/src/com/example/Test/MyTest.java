package com.example.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import com.example.AppInternetCon.InternetUser;
import com.example.Constellation.SaxService;
import com.example.ImportExport.JsonToSDtxt;

import android.content.Context;
import android.test.AndroidTestCase;

public class MyTest extends AndroidTestCase {

	public MyTest() {
		// TODO Auto-generated constructor stub
	}
	public void CreateTable(){
	
	}
	public void SaveFile(){
		Context context =getContext();
		JsonToSDtxt jsonToSDtxt =new JsonToSDtxt(context);
		jsonToSDtxt.LetJsonTotxt();
	}
	
	public static void main(String[] args){
		String path ="http://api.uihoo.com/astro/astro.http.php?fun=day&id=0&format=xml";
		InputStream inputStream = InternetUser.getJsonContentfromInt(path, 10000);
		try {
			List<HashMap<String, String>> list =SaxService.readXML(inputStream, "root");
			for(HashMap<String, String> map:list){
				System.out.println(map.toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
