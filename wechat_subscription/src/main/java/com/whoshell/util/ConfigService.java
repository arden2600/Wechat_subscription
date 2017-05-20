/**   
 * 类名：ConfigService
 *
 */
package com.whoshell.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 读取并解析配置文件config.properties
 * config.properties分为多个section，每个section有多个properties.
 * 通过ConfigService的静态方法getValue可以根据section property的名称获取相应的配置值
 * 配置文件config.properties必须在ClassPath路径上
 *
 * @version 1.0
 * @author zengqaowang
 * @modified 2015-12-14 v1.0 zengqaowang 新建 
 */

public final class ConfigService {

    /** logger. */
	private static Log log  =  LogFactory.getLog(ConfigService.class);

    /**
     * 内部使用Map, key是section的名称，value是该section包含的Properties.
     */
    private static Map<String, Properties> sectionMap =
            new HashMap<String, Properties>();

    /** 配置文件名称. */
    private static final String CONFIG_FILE_NAME = "config.properties";

    /* 初始化区域，读取config.properties的内容并构建内部的Map. */
    static {
		String sTempPath = System.getProperty("classesPathXBD");
		
		String sPath = sTempPath+CONFIG_FILE_NAME;
		log.info(">>path: " + sPath);
        BufferedReader reader = null;
        try {
        	reader = new BufferedReader(new FileReader(sPath));
            /* 分行读取配置文件内容并解析之 */
            String line = null;
            String currentSectionName = null;
            Properties currentProperties = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0 || line.charAt(0) == '#') {
                    /* 忽略空行与注释行 */
                    continue;
                }
                if (line.matches("\\[.*\\]")) {
                    /* 本行是个section定义行，创建新的section并加入map*/
                    currentSectionName = line.replaceFirst("\\[(.*)\\]", "$1");
                    currentProperties =  new Properties();
                    sectionMap.put(currentSectionName, currentProperties);
                } else if (line.matches(".*=.*")) {
                    /* 本行是个property定义行，读取property的名称与值并记录*/
                    if (currentProperties != null) {
                        int i = line.indexOf('=');
                        currentProperties.setProperty(
                                line.substring(0, i).trim(),
                                line.substring(i + 1).trim());
                    }
                }
            }
            reader.close();
//            logAllProperties();
        } catch (FileNotFoundException e) {
            /* 未找到config.properties */
        	log.error("Unable to find property file.");
        } catch (IOException e) {
            /* 无法读取config.properties的内*/
        	log.error("Unable to read content of property file.");
        }
    }

    /**
     * 返回指定section与property在config.properties中的配置
     * @param sectionName section name
     * @param propertyName property name
     * @return 返回sectionName与propertyName应的配置值，如果没有相关定义，返回null值
     */
    public static String getValue(
            final String sectionName, final String propertyName) {

        String value = null; // 返回null
        Properties properties =
                (Properties) ConfigService.sectionMap.get(sectionName);
        if (properties != null) {
            value = properties.getProperty(propertyName);
        }
        return value;
    }

    /** 私有构造方法避免实例化本类. */
    private ConfigService() {
    }
}
