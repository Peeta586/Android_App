package com.example.Weather.cityDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


import com.example.mainsurface.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class FileService {
	private Context context;

	public FileService(Context context) {
		this.context = context;
	}

	public FileService() {
		// TODO Auto-generated constructor stub
	}

	public SQLiteDatabase saveWeatherDBToSD() {
		SQLiteDatabase db=null;
		FileOutputStream fileOutputStream = null;
		//出现过问题，是路径dirPath的原因
		//路径一定要正确，否则后面file.createNewFile();会IO异常
		String dirPath="/data/data/com.example.mainsurface/databases";
	    File dir = new File(dirPath);
	    if(!dir.exists()) {
	        dir.mkdir();
	    }
		File file = new File(dir,"mycity.db");
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			try {
				if(!file.exists()) {
		            file.createNewFile();
		        }
				Log.i("file", "raw");
				InputStream is = context.getResources().openRawResource(R.raw.myweather);
				fileOutputStream = new FileOutputStream(file);
				byte[] buffer = new byte[is.available()];
				is.read(buffer);
				fileOutputStream.write(buffer);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				
				if(fileOutputStream !=null){
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		db = SQLiteDatabase.openOrCreateDatabase(file, null);
		return db;
	}
}
