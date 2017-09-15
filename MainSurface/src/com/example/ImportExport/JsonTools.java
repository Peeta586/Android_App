package com.example.ImportExport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author LSHM
 *将List类型的数据封装成JsonObject
 */
public class JsonTools {

	public JsonTools() {
		// TODO Auto-generated constructor stub
	}
	/**此处有问题：
	 * value是List<JSONObject>形式
	 * 但是直接jsonObject.put(key,value);的出来的形式不是json的形式。每个"会变成\",
	 * 所以现将List<JSONObject>转化为JSONArray然后就可以了，
	 * 对应着JsonToSDTxt类中的此处：JSONArray jsonArray = jsonObject.getJSONArray("Schedules");
	 * @param key
	 * @param value
	 * @return
	 */
	public static String createJsonString(String key,Object value){
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray =null;
		try {
			jsonArray= new JSONArray(value.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			jsonObject.put(key,jsonArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject.toString();
		
	}

}
