package edu.duke.cloudnote.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cloudnote.dao.UserMybatisDao;
import edu.duke.cloudnote.entity.User;
import edu.duke.cloudnote.util.Md5Utils;
import edu.duke.cloudnote.util.UUIDUtil;


@Component("userService")
@Transactional
public class UserService {
	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Resource(name = "userMybatisDao")
	private UserMybatisDao userMybatisDao;

	/**
	 * check userName and password
	 * @Title: getUserByLoginNamePsd 
	 * @return User
	 */
	public User getUserByLoginNamePsd(String loginName, String pwd) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cnUserName", loginName);
		param.put("cnUserPassword", Md5Utils.md5(pwd));
		logger.debug("validate user["+param+"]");
		User nowUser = null;
		nowUser = userMybatisDao.getUserByLoginNamePwd(param);
		return nowUser;
	}
	/**
	 * find user by userName
	 * @Title: findUserByLoginName  
	 * @return User
	 */
	public User findUserByLoginName(String loginName) {
		User nowUser = userMybatisDao.getUserByLoginName(loginName);
		return nowUser;
	}

	
	/**
	 * user registration
	 * @Title: createUser 
	 * @return void
	 */
	public void createUser(User user) {
			user.setCnUserId(UUIDUtil.getUId());
			user.setCnUserPassword(Md5Utils.md5(user.getCnUserPassword()));
			userMybatisDao.createUser(user);
	}
	

	/**
	 * reset password
	 * @Title: resetPwd 
	 * @return void
	 */
	public void resetPwd (String userId, String pwd) {
			User user = new User();
			user.setCnUserId(userId);
			user.setCnUserPassword(Md5Utils.md5(pwd));
			userMybatisDao.updatePassword(user);
	}
	
}
