package com.example.Weather;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebServiceUtil {
	// 定义Web Service的命名空间
	private static final String SERVICE_NS ="http://WebXml.com.cn/";
	// 定义Web Service提供服务的URL
	private static final String SERVICE_URL = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";
	public WebServiceUtil(){
		
	}
	
	public static SoapObject getWeatherByCity(String cityCode){
		// 根据城市或地区名称查询获得未来三天内天气情况、现在的天气实况、天气和生活指数
		String methodName = "getWeather";
		//超时时间设为10s  koap2——3.0版本以后支持超时设置，本应用用的是3.0.2
		HttpTransportSE httpTranstation = new HttpTransportSE(SERVICE_URL,10000);
		httpTranstation.debug = true;
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
		soapObject.addProperty("theCityCode", cityCode);
		envelope.bodyOut = soapObject;
		envelope.dotNet = true;
		
		try
		{
			// 调用Web Service
			httpTranstation.call(SERVICE_NS + methodName, envelope);
			if (envelope.getResponse() != null)
			{
				// 获取服务器响应返回的SOAP消息
				SoapObject result = (SoapObject) envelope.bodyIn;
				SoapObject detail = (SoapObject) result.getProperty(methodName
						+ "Result");
				// 解析服务器响应的SOAP消息。
				return detail;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
