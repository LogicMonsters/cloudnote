package edu.duke.cloudnote.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//import edu.duke.cloudnote.service.NoteBookService;


import edu.duke.cloudnote.entity.Response;
import edu.duke.cloudnote.entity.User;
import edu.duke.cloudnote.service.UserService;
import edu.duke.cloudnote.util.Md5Utils;
import edu.duke.cloudnote.util.UUIDUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource(name = "userService")
	private UserService userService;

	
	/**
	 * login check
	 * @Title: login 
	 * @return Response
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Response login(HttpServletRequest request, HttpServletResponse response) {
		Response message = new Response();
		message.setStatus(-2); 
		message.setMessage("username or password is wrong！");
		
		String auth = request.getHeader("Authorization");
//		"Basic DGFSRED%$E%%R^&TDGGFDRES"
//		"DGFSRED%$E%%R^&TDGGFDRES"
//		"username:password"
		User loginUser = new User();
		try{
			if(StringUtils.isNotEmpty(auth)){
				String auths[] = auth.split(" ");
				if(auths.length == 2){
					String dec[] = new String (Base64.decodeBase64(auths[1].getBytes("utf-8"))).split(":");
					logger.debug("decoded username and password is["+dec+"]");
					if(dec.length == 2){
						loginUser = userService.getUserByLoginNamePsd(dec[0], dec[1]);
						if(loginUser != null){
							message.setStatus(1);
							message.setResource(loginUser);
							message.setMessage("verification success！");
						}
					}
				}
			}
			
			//  set user's token
			String tk = UUIDUtil.getUId();
			request.getSession().setAttribute("userToken", tk);			
			response.setHeader("Authorization", "Token "+tk);
		}catch(Exception e){
			e.printStackTrace();
			message.setMessage("login failed!");
			message.setStatus(-1);
		}
		return message;
	}

	/**
	 * registration
	 * @Title: createUser 
	 * @return Response
	 */
	@RequestMapping(value = "/createUser", method = RequestMethod.POST,headers = "Accept=application/json")
	@ResponseBody
	public Response createUser(
			@RequestBody User user,
			HttpServletRequest request, 
			HttpServletResponse response) {
		Response message = new Response();
		try{
			if(user != null){
				userService.createUser(user);
				message.setResource(user);
			
			}
		}catch(Exception e){
			e.printStackTrace();
			message.setMessage("login failed!");
			message.setStatus(-1);
		}
		return message;
	}
	
	/**
	 * check userName
	 * @Title: checkUserName 
	 * @return Response
	 */
	@RequestMapping(value = "/checkUserName/{userName}", method = RequestMethod.POST,headers = "Accept=application/json")
	@ResponseBody
	public Response checkUserName(
			@PathVariable String userName,
			HttpServletRequest request, 
			HttpServletResponse response) {
		Response message = new Response();
		try{
			// check whether user exists
			if(StringUtils.isNotEmpty(userName)){
				User user = userService.findUserByLoginName(userName);
				if(user == null){
					message.setMessage("username is available!");
				}else{
					message.setStatus(-2);
					message.setMessage("username is not available!");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message.setMessage("username validation exception!");
			message.setStatus(-1);
		}
		return message;
	}
	
	
	
	/**
	 * check user password
	 * @Title: checkUserOldpwd 
	 * @return Response
	 */
	@RequestMapping(value = "/checkUserOldpwd/{userName}/{oldPwd}", method = RequestMethod.POST,headers = "Accept=application/json")
	@ResponseBody
	public Response checkUserOldpwd(
			@PathVariable String userName,
			@PathVariable String oldPwd,
			HttpServletRequest request, 
			HttpServletResponse response) {
		Response message = new Response();
		try{
			String decodePwd = new String(Base64.decodeBase64(oldPwd.getBytes("utf-8")));
			if(StringUtils.isNotEmpty(decodePwd)){
				User user = userService.findUserByLoginName(userName);
				if(user != null && Md5Utils.md5(decodePwd).equals(user.getCnUserPassword())){
					message.setStatus(1);
					message.setMessage("pwd is correct!");
				}else{
					message.setStatus(-2);
					message.setMessage("pwd is incorrect!");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message.setMessage("pwd validation exception！");
			message.setStatus(-1);
		}
		return message;
	}
	
	
	
	
	
	/**
	 * reset pwd
	 * @Title: resetPwd 
	 * @return Response
	 */
	@RequestMapping(value = "/resetPwd/{userId}/{pwd}", method = RequestMethod.POST,headers = "Accept=application/json")
	@ResponseBody
	public Response resetPwd(
			@PathVariable String userId,
			@PathVariable String pwd,
			HttpServletRequest request, 
			HttpServletResponse response) {
		Response message = new Response();
		
		try{			
			String decodePwd = new String (Base64.decodeBase64(pwd.getBytes("utf-8")));
			String auth = request.getHeader("Authorization");			
			if(StringUtils.isNotEmpty(auth)){
				String auths[] = auth.split(" ");				
				if(auths.length == 2){				
					String dec[] = new String (Base64.decodeBase64(auths[1].getBytes("utf-8"))).split(":");		
					if(dec.length == 2 && dec[1].equals(request.getSession().getAttribute("userToken"))){				
						if(StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(decodePwd)){
							userService.resetPwd(userId, decodePwd);								
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message.setMessage("pwd reset exception");
			message.setStatus(-1);
		}
		return message;
	}
}
