package com.example.AppInternetCon;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;


public class InternetUser {
	//private static final int REQUEST_TIMEOUT = 10*1000;//设置请求超时10秒钟
	//private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟
	private static HttpResponse httpResponse  =null;
	private static HttpEntity httpEntity = null;
	public InternetUser() {

	}
	private static HttpClient getHttpClient(int TimeOutRW){
			BasicHttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, TimeOutRW);
			HttpConnectionParams.setSoTimeout(httpParams, TimeOutRW);
			HttpClient client = new DefaultHttpClient(httpParams);
			return client;
	}
	public static InputStream getJsonContentfromInt(String url_path, int TimeOut) {
			HttpGet httpGet = new HttpGet(url_path);
			
			HttpClient httpClient = getHttpClient(TimeOut);
			InputStream inputStream = null;
			try {
				httpResponse = httpClient.execute(httpGet);
				httpEntity = httpResponse.getEntity();
				inputStream = httpEntity.getContent();
				//return inputStream;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return inputStream;
	}
	public static String getJsonContent(String url_path, int TimeOut) {
		return changeInputStream(getJsonContentfromInt(url_path,TimeOut));
	}

	private static String changeInputStream(InputStream inputStream) {
		String jsonString = "";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int len = 0;
		byte[] data = new byte[1024];
		try {
			while ((len = inputStream.read(data)) != -1) {
				outputStream.write(data, 0, len);
			}
			jsonString = new String(outputStream.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				inputStream.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		return jsonString;
	}
}
