package com.example.Constellation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandle extends DefaultHandler {
	private HashMap<String, String> map = null;// 存储单个解析的完整对象
	private List<HashMap<String, String>> list = null;// 存储所有的解析对象
	private String currentTag = null;// 正在解析的元素的标签
	//private String currentValues = null;// 解析当前元素的值
	private String nodeName = null;// 解析当前节点的名称
	private StringBuilder builder =new StringBuilder();  

	public MyHandle(String nodeName) {
		this.nodeName = nodeName;
	}

	public List<HashMap<String, String>> getList() {
		return list;
	}

	@Override
	public void startDocument() throws SAXException {
		// 当读到第一个开始标签的时候，会触发这个方法
		list = new ArrayList<HashMap<String, String>>();
	}
	/**错误：不能解析这种结构的，只能把item之间的值取出，************************************
	 * <item>
<title>爱情运势</title>
<title2>
<item>有对象:</item>
<item>没对象:</item>
</title2>
<rank>
<item>5</item>
<item>3</item>
</rank>
<value2>
<item>感情稳定发展，与另一半相处上也很有乐趣，有许多属於两人的时光，可以充分享受甜蜜的恋爱感觉。</item>
<item>与朋友的吃喝玩乐上开销增加，你外出跟人聚会的机会增加，你可能还会花不少钱请客，要有心理准备！</item>
</value2>
</item>
	 */

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		builder.setLength(0);
		// 当遇到文档的开头的时候，调用这个方法
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
	 * 没错，就是这样， startElement  读取起始标签， endElement 读取结束标签，characters 呢？当然是读取其值， 这没错，
	 * 但是大家都天真的以为 
	 * characters 只执行一次，并且一次就读取了全部内容。错就错在这！其实characters 是很有可能会执行多次的，
	 * 当遇到内容中有回车，\t等等内容时，它很有可能就执行多次。 有的人可能会说，那我没有这些是不是就只执行一次了？ 看下我实测结果：
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// 这个方法是用来处理xml文件所读到的内容
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
		 * 不能在此处currentTag和currentValues赋值为null因为有可能一个内容characters多次执行
		 */
//		currentTag =null;//把当前的结点的对应的值和标签设置为空
//		currentValues=null;
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(currentTag !=null)//要判断是否为空
			map.put(currentTag, builder.toString());
		currentTag =null;//把当前的结点的对应的值和标签设置为空
		//currentValues=null;
		//遇到结束标记的时候会调用这个方法
		if(qName.equals(nodeName)){
			if(map !=null)
				list.add(map);
			map =null;
		}
		//super.endElement(uri, localName, qName);
	}

}
