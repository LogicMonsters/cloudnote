package edu.duke.cloudnote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;







import edu.duke.cloudnote.dao.UserMybatisDao;
import edu.duke.cloudnote.entity.User;

/**
 *
 * business management class
 *
 */
// Spring Service Bean
@Component
@Transactional
public class AccountService {


	@Autowired
	private UserMybatisDao userMybatisDao;

	/**
	 * find user by userName
	 */
	public User findUserByLoginName(String loginName) {
		User nowUser = userMybatisDao.getUserByLoginNamePwd(null);
		System.out.println("CnUserPassword:"+nowUser.getCnUserPassword());
		return nowUser;
	}


}
