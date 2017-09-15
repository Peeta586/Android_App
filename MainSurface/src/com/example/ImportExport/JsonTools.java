package com.example.ImportExport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author LSHM
 *��List���͵����ݷ�װ��JsonObject
 */
public class JsonTools {

	public JsonTools() {
		// TODO Auto-generated constructor stub
	}
	/**�˴������⣺
	 * value��List<JSONObject>��ʽ
	 * ����ֱ��jsonObject.put(key,value);�ĳ�������ʽ����json����ʽ��ÿ��"����\",
	 * �����ֽ�List<JSONObject>ת��ΪJSONArrayȻ��Ϳ����ˣ�
	 * ��Ӧ��JsonToSDTxt���еĴ˴���JSONArray jsonArray = jsonObject.getJSONArray("Schedules");
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
