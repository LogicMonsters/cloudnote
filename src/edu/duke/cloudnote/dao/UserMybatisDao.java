package edu.duke.cloudnote.dao;

import java.util.Map;

import edu.duke.cloudnote.entity.User;

/**
 * scan all interfaces through @MapperScannerConfigurer, implement dynamically in Spring Context
 * method must be identical to the ones in Mapper.xml
 */
@MyBatisRepository
public interface UserMybatisDao {
	
	//find user info by userName and password
	public User getUserByLoginNamePwd(Map<String, Object> params);
	// create user
	public void createUser(User user);
	// change password by userId
	public void updatePassword(User user);
	// find user by userName
	public User getUserByLoginName(String cnUserName);

}
