package com.whoshell.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
public class HttpClient {

	private static Log log = LogFactory.getLog(HttpClient.class);
	
	/**
	 * requestGet:发送get请求
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String requestGet(String url) throws IOException {
		String content = "";
		BufferedReader bufferedReader = null;
		//创建httpclient
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		try {

			//创建httpget
			HttpGet httpGet = new HttpGet(new URI(url));
			//执行get方法
			CloseableHttpResponse response = httpclient.execute(httpGet);
			try {
				
				bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				StringBuffer sb = new StringBuffer("");
				String line = "";
				String NL = System.getProperty("line.separator");
				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line + NL);
				}
				content = sb.toString();
			} catch (Exception e) {
				log.error("获取get请求响应结果失败：", e);
			}finally {
				response.close();
			}
			
		} catch (Exception e) {
			log.error("get请求发送失败：", e);
		}finally {
			httpclient.close();
			if(bufferedReader != null) {
				bufferedReader.close();
			}
		}
		log.info("content内容：---"+ content);
		return content;
	}
	
	/**
	 * requestPost:Post发送方式
	 * 
	 * @param url 提交地址
	 * @param formList 表单数据
	 * @return
	 * @throws IOException 
	 */
	public static String requestPost(String url, String formParam) throws IOException {
		String content = "";
		//创建httpclient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			//创建post请求
			HttpPost httpPost = new HttpPost(new URI(url));
//			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formList, "UTF-8");
//			//绑定表单数据
//			httpPost.setEntity(uefEntity);
			
			httpPost.setEntity(new StringEntity(formParam, Charset.forName("UTF-8")));
			
			//执行post请求
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				content = EntityUtils.toString(entity, "UTF-8");
			} catch (Exception e) {
				log.error("获取post请求响应数据失败：", e);
			}finally {
				response.close();
			}
		} catch (Exception e) {
			log.error("发送post请求失败：", e);
		}finally {
			httpClient.close();
		}
		log.info("获取的post数据："+content);
		return content;
	}
	
	/**
	 * httpRequestReturnByJson : 发送一个http请求，返回json对象。
	 * @param requestUrl
	 * @param requestMethod
	 * @param outputStr
	 * @return
	 */
	   public static JSONObject httpRequestReturnByJson(String requestUrl,String requestMethod, String outputStr) {  
	        JSONObject jsonObject = null;  
	        StringBuffer buffer = new StringBuffer();  
	        try {  
	            TrustManager[] tm = { new X509TrustManager() {
					public void checkClientTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {
					}
					public void checkServerTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {
					}
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
	            } };  
	            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
	            sslContext.init(null, tm, new java.security.SecureRandom());  
	            SSLSocketFactory ssf = sslContext.getSocketFactory();  
	            URL url = new URL(requestUrl);  
	            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
	            httpUrlConn.setSSLSocketFactory(ssf);  
	            httpUrlConn.setDoOutput(true);  
	            httpUrlConn.setDoInput(true);  
	            httpUrlConn.setUseCaches(false);  
	            httpUrlConn.setRequestMethod(requestMethod);  
	            if ("GET".equalsIgnoreCase(requestMethod))  
	                httpUrlConn.connect();  
	            if (null != outputStr) {  
	                OutputStream outputStream = httpUrlConn.getOutputStream();  
	                outputStream.write(outputStr.getBytes("UTF-8"));  
	                outputStream.close();  
	            }  
	            InputStream inputStream = httpUrlConn.getInputStream();  
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
	            String str = null;  
	            while ((str = bufferedReader.readLine()) != null) {  
	                buffer.append(str);  
	            }  
	            bufferedReader.close();  
	            inputStreamReader.close();  
	            inputStream.close();  
	            inputStream = null;  
	            httpUrlConn.disconnect();  
//	            jsonObject = JSONObject.fromObject(buffer.toString());  
	            jsonObject = JSONObject.parseObject(buffer.toString());
	        } catch (ConnectException ce) {  
	            ce.printStackTrace();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return jsonObject;  
	    }  
	   
	   /**
	    * postJsonDataRequest : 以json格式数据post提交请求
	    * @param requestUrl
	    * @param jsonData
	    * @return
	    */
	   public static String postJsonDataRequest(String requestUrl,JSONObject jsonData) {
		   String content = "";
		   try {
			URL url = new URL(requestUrl);
			 // 建立http连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置允许输出
            conn.setDoOutput(true);

            conn.setDoInput(true);

            // 设置不用缓存
            conn.setUseCaches(false);
            // 设置传递方式
            conn.setRequestMethod("POST");
            // 设置维持长连接
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置文件字符集:
            conn.setRequestProperty("Charset", "UTF-8");
            //转换为字节数组
            byte[] data = (jsonData.toString()).getBytes();
            // 设置文件长度
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));

            // 设置文件类型:
            conn.setRequestProperty("contentType", "application/json");
            
            // 开始连接请求
            conn.connect();
            OutputStream  out = conn.getOutputStream();     
            // 写入请求的字符串
            out.write((jsonData.toString()).getBytes());
            out.flush();
            out.close();
            // 请求返回的状态
            if (conn.getResponseCode() == 200) {
               log.info("post提交json数据响应成功");
                // 请求返回的数据
                InputStream in = conn.getInputStream();
                byte[] data1 = new byte[in.available()];
                in.read(data1);
                // 转成字符串
                content = new String(data1);
               log.info("提交处理响应字符串:>>" + content);
               in.close();
            } else {
               log.info("post提交json数据响应失败");
            }

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return content;
	   }
	   
}
