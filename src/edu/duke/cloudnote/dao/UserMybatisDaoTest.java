package edu.duke.cloudnote.dao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cloudnote.entity.User;
import edu.duke.cloudnote.util.Md5Utils;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
@ActiveProfiles("test")
public class UserMybatisDaoTest {
	
	@Resource
	private UserMybatisDao userMybatisDao;
	
	@Test
	@Transactional    
	@Rollback(false)   
	public void getNoteActivityDetailTest(){
		Map<String, Object> param = new HashMap<String, Object>();//encapsulate the parameter of DAO
		param.put("cnUserName", "test1");//login name
		param.put("cnUserPassword", Md5Utils.md5("123456"));//encode by MD5
		User userByLoginNamePsd = userMybatisDao.getUserByLoginNamePwd(param);
		System.out.println("noteActivityDetail = " + userByLoginNamePsd);
		Assert.assertNotNull(userByLoginNamePsd);
	}
	
	
	@Test
	@Transactional   
	@Rollback(true)  
	public void createUserTest(){
		
		User user = new User();
		user.setCnUserId("123123111");
		user.setCnUserName("user_test_1");
		user.setCnUserPassword("pwd1111");
		user.setCnUserToken("1111");
		userMybatisDao.createUser(user);
		System.out.println("add user success" );

	}
	
	
	@Test
	@Transactional    
	@Rollback(true)    
	public void updatePasswordTest(){
		
		User user = new User();
		user.setCnUserId("123123111");
		user.setCnUserName("user_test_1");
		user.setCnUserPassword("pwd11112222");
		user.setCnUserToken("1111");
		userMybatisDao.updatePassword(user);
		System.out.println("add user success" );

	}
	
	
	@Test
	@Transactional   
	@Rollback(false) 
	public void getUserByLoginNameTest(){
		User userByLoginName = userMybatisDao.getUserByLoginName("test1");
		System.out.println("userByLoginName = " + userByLoginName);
		Assert.assertNotNull(userByLoginName);
	}
	
	
}
