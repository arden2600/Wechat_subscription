package com.whoshell.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.whoshell.service.AccessTokenService;

/**
 * AccessTokenInit :服务器启动,初始化全局token.
 * @author xianSky
 *
 */
public class AccessTokenInit implements ServletContextListener{

	public void contextInitialized(ServletContextEvent sce) {
			AccessTokenService.refresh();
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
