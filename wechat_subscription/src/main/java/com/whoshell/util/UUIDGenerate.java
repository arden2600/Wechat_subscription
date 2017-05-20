/**   
 * 类名：UUIDGenerate
 *
 */
package com.whoshell.util;

import org.springframework.stereotype.Component;

import com.fasterxml.uuid.Generators;

/** 
 * UUIDGenerate: UUID工具类
 * 
 * @version 1.0
 * @author 15989
 * @modified 2016-7-20 v1.0 15989 新建 
 */
@Component("uuidGenerate")
public class UUIDGenerate {

	/**
	 * 
	 * genericLoginIdentity: 生成唯一标识
	 * 
	 * @return 唯一标识
	 * @throws
	 */
	public String genericIdentity() {
		return Generators.timeBasedGenerator().generate().toString().replace("-", "");
	}
}
