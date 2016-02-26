package edu.duke.cloudnote.service;


import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.duke.cloudnote.entity.User;


@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
@ActiveProfiles("test")
public class AccountServiceTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Resource
	private AccountService accountService;
	
	@Test

	public void getActionNoteListByCodeTest(){
		User findUserByLoginName = accountService.findUserByLoginName("test1");
		System.out.println("findUserByLoginName=" + findUserByLoginName);
		Assert.assertNotNull(findUserByLoginName);
	}
	
}
