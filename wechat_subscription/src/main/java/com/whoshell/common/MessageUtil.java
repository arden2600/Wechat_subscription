package com.whoshell.common;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.whoshell.common.pojo.message.resp.Article;
import com.whoshell.common.pojo.message.resp.MusicMessage;
import com.whoshell.common.pojo.message.resp.NewsMessage;
import com.whoshell.common.pojo.message.resp.TextMessage;

/**
 * MessageUtil : 微信公众号中消息处理工具类
 * 将文本，音乐，图文消息转换成XML可以参考官方的输出xml格式进行构造
 * @author XianSky
 *
 */
public class MessageUtil {

	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT="text";
	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC="music";
	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS="news";
	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT="text";
	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE="image";
	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK="link";
	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION="location";
	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE="voice";
	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT="event";
	/**
	 * 请求消息类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE="subscribe";
	/**
	 * 请求消息类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE="unsubscribe";
	/**
	 * 事件类型：CLICK(自定义菜单单击事件)
	 */
	public static final String EVENT_TYPE_CLICK="click";
	/**
	 * 接收事件推送：SCAN(已关注公众号扫码)
	 */
	public static final String EVENT_TYPE_SCAN="scan";

	
	/**
	 * 
	 * parseXml: 解析微信发来的请求(XML)
	 *
	 * @param request
	 * @return 
	 * @throws Exception
	 */
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception{
		//将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		InputStream inputStream = null;
		try {
			//从request中取得输入流
			inputStream = request.getInputStream();
			//读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			//得到XML根元素
			Element root = document.getRootElement();
			//得到根元素的所有子节点
			List<Element> elementList = root.elements();
			
			//遍历所有子节点
			for (Element e:elementList) {
				map.put(e.getName(), e.getText());
				System.out.println("name: " + e.getName() + "\ttext: " + e.getText());
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//释放资源
			if (inputStream != null) {
				inputStream.close();
				inputStream = null;
			}
		}

		return map;
	}
	
	/**
	 * 
	 * textMessageToXML: 文本消息对象转换成XML
	 *
	 * @param textMessage 文本消息对象
	 * @return xml
	 */
	public static String textMessageToXML(TextMessage textMessage) {
		xStream.alias("xml", textMessage.getClass());
		return xStream.toXML(textMessage);
	}
	
	/**
	 * 
	 * musicMessageToXML: 音乐消息对象转换成xml
	 *
	 * @param musicMessage 音乐消息对象
	 * @return xml
	 */
	public static String musicMessageToXML(MusicMessage musicMessage) {
		xStream.alias("xml", musicMessage.getClass());
		return xStream.toXML(musicMessage);
	}
	
	/**
	 * 
	 * newsMessageToXML: 图文消息对象转换成XML
	 *
	 * @param newsMessage 图文消息对象
	 * @return xml
	 */
	public static String newsMessageToXML(NewsMessage newsMessage) {
		xStream.alias("xml", newsMessage.getClass());
		xStream.alias("item", new Article().getClass());
		return xStream.toXML(newsMessage);
	}
	
	private static XStream xStream = new XStream(new XppDriver(){
		public HierarchicalStreamWriter createWriter(Writer out){
			return new PrettyPrintWriter(out) {
				//对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;
				public void startNode(String name,Class clazz) {
					super.startNode(name, clazz);
				}
				
				protected void writeText(QuickWriter writer,String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
		
	});
}
