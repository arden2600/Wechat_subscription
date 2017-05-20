/**   
 * 类名：NamePasswordEncryptUtil
 *
 */
package com.whoshell.util;

import java.security.Security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.cybbj.JKeyParmater;
import com.cybbj.Mechanism;
import com.cybbj.desoperator.DESGenerate;
import com.cybbj.util.Converts;

/** 
 * NamePasswordEncryptUtil: 数据配置文件用户名密码加密工具类
 * 
 * @version 1.0
 * @author 15989
 * @modified 2016-7-14 v1.0 15989 新建 
 */
public class NamePasswordEncryptUtil {
	
	private static Log log = LogFactory.getLog(NamePasswordEncryptUtil.class);
	private static DESGenerate desGenerate = null;
	private static Mechanism keyGen = null;
	private static JKeyParmater key = null;
	
	//产生加解密随机秘钥
	static {
		try {	
			Security.addProvider(new BouncyCastleProvider());
			desGenerate = new DESGenerate();
			keyGen = new Mechanism(Mechanism.DES_KEY);
			key = new JKeyParmater(keyGen.getMechanismType(), "D33C36B1B5FC2CB1".getBytes("UTF-8"));
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	/**
	 * 
	 * getEncryptString: 对字符串进行DES加密,返回BASE64编码的加密字符串
	 *
	 * @param str 待加密字符串
	 * @return 加密后字符串
	 */
	public static String getEncryptString(String str) {
		BASE64Encoder base64Encoder = new BASE64Encoder();
		byte[] encryptStrBytes = new byte[]{};
		try {
			byte[] strBytes = str.getBytes("UTF-8");
			encryptStrBytes = desGenerate.encryptByPKCS(key, strBytes);
		} catch (Exception e) {
			log.error(e);
		}
		return base64Encoder.encode(encryptStrBytes);
	}
	
	/**
	 * 
	 * getDecryptString: 对BASE64编码的加密字符串进行解密,返回解密后的字符串
	 *
	 * @param str  待解密字符串
	 * @return 解密后字符串
	 */
	public static String getDecryptString(String str) {
		BASE64Decoder base64Decoder = new BASE64Decoder();
		String decryptStr = "";
		try {
			byte[] strBytes = base64Decoder.decodeBuffer(new String(str.getBytes("UTF-8")));
			byte[] decryptStrBytes = desGenerate.decryptByPKCS(key, strBytes);
			decryptStr =  new String(decryptStrBytes,"UTF-8");
		} catch (Exception e) {
			log.error(e);
		}
		return decryptStr;
	}
}
