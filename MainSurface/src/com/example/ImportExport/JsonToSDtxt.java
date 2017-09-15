package com.example.ImportExport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

/**
 * @author LSHM
 *将Json数据导出到mingli文件夹下的Schdule文件中。
 */
public class JsonToSDtxt {
	private Context context;
	private String filename ="schedule.txt";
	public JsonToSDtxt(Context context) {
		this.context =context;
	}
	public void LetJsonTotxt(){
		/**
		 * 1.获得jsonObject对象，通过JsonObject.toString获得String，
		 * 然后将String以txt方式输出
		 * 2.将对象写入txt中
		 */
		JsonService jsService = new JsonService(context);
		String str = JsonTools.createJsonString("Schedules",jsService.getListScheduleJson());
		FileServiceForSch fileSch = new FileServiceForSch(context);
		boolean flag =fileSch.saveSchduleToSD(filename,str);
		Log.i("tag","flag->"+flag);
	}
	public List<Map<String,Object>> GetJsonFromSD(){
		FileServiceForSch fileSch = new FileServiceForSch(context);
		String mesString = fileSch.getFileFromSDcard(filename);
		Log.i("test","->>"+mesString);
		//JSONObject jsonObject=null;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
 		try {
			JSONObject jsonObject = new JSONObject(mesString);
			JSONArray jsonArray = jsonObject.getJSONArray("Schedules");
			for(int i=0;i<jsonArray.length();i++){
				JSONObject json2 = jsonArray.getJSONObject(i);
				Map<String, Object> map = new HashMap<String, Object>();
				@SuppressWarnings("unchecked")
				Iterator<String> iterator =json2.keys();
				while(iterator.hasNext()){
					String json_key =iterator.next();
					Object json_value =json2.get(json_key);
					if(json_value == null){
						json_value="";
					}
					map.put(json_key, json_value);
				}
				list.add(map);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		return list;
	}
	
}
