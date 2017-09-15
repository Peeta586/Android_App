package com.example.Constellation;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SaxService {

	public SaxService() {
		// TODO Auto-generated constructor stub
	}
	public  static List<HashMap<String, String>> readXML(
			InputStream inputStream,String nodeName){
		//List<HashMap<String, String>> list =null;
		try{
		SAXParserFactory spf =SAXParserFactory.newInstance();
		SAXParser parser= spf.newSAXParser();
		MyHandle handle = new MyHandle(nodeName);
		parser.parse(inputStream, handle);
		inputStream.close();
		return handle.getList();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

}
