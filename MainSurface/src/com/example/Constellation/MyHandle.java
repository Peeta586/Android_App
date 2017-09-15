package com.example.Constellation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandle extends DefaultHandler {
	private HashMap<String, String> map = null;// �洢������������������
	private List<HashMap<String, String>> list = null;// �洢���еĽ�������
	private String currentTag = null;// ���ڽ�����Ԫ�صı�ǩ
	//private String currentValues = null;// ������ǰԪ�ص�ֵ
	private String nodeName = null;// ������ǰ�ڵ������
	private StringBuilder builder =new StringBuilder();  

	public MyHandle(String nodeName) {
		this.nodeName = nodeName;
	}

	public List<HashMap<String, String>> getList() {
		return list;
	}

	@Override
	public void startDocument() throws SAXException {
		// ��������һ����ʼ��ǩ��ʱ�򣬻ᴥ���������
		list = new ArrayList<HashMap<String, String>>();
	}
	/**���󣺲��ܽ������ֽṹ�ģ�ֻ�ܰ�item֮���ֵȡ����************************************
	 * <item>
<title>��������</title>
<title2>
<item>�ж���:</item>
<item>û����:</item>
</title2>
<rank>
<item>5</item>
<item>3</item>
</rank>
<value2>
<item>�����ȶ���չ������һ���ദ��Ҳ������Ȥ�������������˵�ʱ�⣬���Գ���������۵������о���</item>
<item>�����ѵĳԺ������Ͽ������ӣ���������˾ۻ�Ļ������ӣ�����ܻ��Ứ����Ǯ��ͣ�Ҫ������׼����</item>
</value2>
</item>
	 */

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		builder.setLength(0);
		// �������ĵ��Ŀ�ͷ��ʱ�򣬵����������
		if (qName.equals(nodeName)) {
			map = new HashMap<String, String>();
		}
		if (attributes != null && map != null) {
			for (int i = 0; i < attributes.getLength(); i++) {
				map.put(attributes.getQName(i), attributes.getValue(i));
			}
		}
		currentTag = qName;
		
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 * û������������ startElement  ��ȡ��ʼ��ǩ�� endElement ��ȡ������ǩ��characters �أ���Ȼ�Ƕ�ȡ��ֵ�� ��û��
	 * ���Ǵ�Ҷ��������Ϊ 
	 * characters ִֻ��һ�Σ�����һ�ξͶ�ȡ��ȫ�����ݡ���ʹ����⣡��ʵcharacters �Ǻ��п��ܻ�ִ�ж�εģ�
	 * �������������лس���\t�ȵ�����ʱ�������п��ܾ�ִ�ж�Ρ� �е��˿��ܻ�˵������û����Щ�ǲ��Ǿ�ִֻ��һ���ˣ� ������ʵ������
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// �����������������xml�ļ�������������
		if (currentTag != null && map != null) {
			String currentvalue =new String(ch, start, length);
			if(!currentvalue.equals("\n"))
				builder.append(ch, start, length);
//			if (currentValues != null && !currentValues.trim().equals("")
//					&& !currentValues.trim().equals("\n")) {
//				map.put(currentTag, currentValues);
//			}
		}
		/**
		 * �����ڴ˴�currentTag��currentValues��ֵΪnull��Ϊ�п���һ������characters���ִ��
		 */
//		currentTag =null;//�ѵ�ǰ�Ľ��Ķ�Ӧ��ֵ�ͱ�ǩ����Ϊ��
//		currentValues=null;
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(currentTag !=null)//Ҫ�ж��Ƿ�Ϊ��
			map.put(currentTag, builder.toString());
		currentTag =null;//�ѵ�ǰ�Ľ��Ķ�Ӧ��ֵ�ͱ�ǩ����Ϊ��
		//currentValues=null;
		//����������ǵ�ʱ�������������
		if(qName.equals(nodeName)){
			if(map !=null)
				list.add(map);
			map =null;
		}
		//super.endElement(uri, localName, qName);
	}

}
