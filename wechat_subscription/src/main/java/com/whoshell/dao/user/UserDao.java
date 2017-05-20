package com.whoshell.dao.user;

import org.apache.ibatis.annotations.Param;

import com.whoshell.pojo.User;

public interface UserDao {

	User getUserByOpenId(@Param(value="openId")String openId);
	
	User getUserByUnionId(@Param(value="unionId")String unionId);
	
}
