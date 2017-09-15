package com.example.Weather;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebServiceUtil {
	// ����Web Service�������ռ�
	private static final String SERVICE_NS ="http://WebXml.com.cn/";
	// ����Web Service�ṩ�����URL
	private static final String SERVICE_URL = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";
	public WebServiceUtil(){
		
	}
	
	public static SoapObject getWeatherByCity(String cityCode){
		// ���ݳ��л�������Ʋ�ѯ���δ��������������������ڵ�����ʵ��������������ָ��
		String methodName = "getWeather";
		//��ʱʱ����Ϊ10s  koap2����3.0�汾�Ժ�֧�ֳ�ʱ���ã���Ӧ���õ���3.0.2
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
			// ����Web Service
			httpTranstation.call(SERVICE_NS + methodName, envelope);
			if (envelope.getResponse() != null)
			{
				// ��ȡ��������Ӧ���ص�SOAP��Ϣ
				SoapObject result = (SoapObject) envelope.bodyIn;
				SoapObject detail = (SoapObject) result.getProperty(methodName
						+ "Result");
				// ������������Ӧ��SOAP��Ϣ��
				return detail;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
