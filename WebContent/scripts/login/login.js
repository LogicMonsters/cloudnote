$(function(){
	
	$("#login").click(function(){
		var loginUserName = $("#count").val();
		var loginPassword = $("#password").val();
		var token = "";
		var userId="";
		
		jQuery.support.cors = true;
		$.ajax({
			type:"post",
			url:basePath + "user/login",
			dataType : "json",
			beforeSend: function(xhr){
				xhr.setRequestHeader('Content-Type', 'application/json');
//				xhr.setRequestHeader('Authorization', loginUserName+":"+loginPassword);
				xhr.setRequestHeader('Authorization',"Basic "+ Base64.encode(loginUserName+":"+loginPassword));
			},
			complete : function(xhr, TS){
				//"Token "+UUID
				//base64(userName:UUID) --> cookie
				token = xhr.getResponseHeader("Authorization");
				var uuid = token.split(" ");
				if(uuid[1] != null){
					token = "Basic "+Base64.encode(loginUserName+":"+uuid[1]);
				}
				addCookie(userId+"_token",token,2);
			},
			success:function(result){
				//token is stored in cookie, will expire in 2 hours
				if(result.resource != null){
					userId = result.resource.cnUserId;
				}else{
					alert(result.message);
					return;
				}
				
				if($.isFunction(loginSuccess)){
					loginSuccess(result);
				}
			},
			error:loginError
		});
	});
	
	
	$("#regist_button").click(function(){
		//allow cross-domain access
		jQuery.support.cors = true;
		var username = $("#regist_username").val();
		var password = $("#regist_password").val();
		
		var user = {
				"cnUserName":username ,
				"cnUserPassword":password
				};

		$.ajax({
			type:"post",
			url : basePath + "user/createUser",
			data:JSON.stringify(user),
			dataType : "json",
			contentType : "application/json",
			beforeSend: function(xhr){
				xhr.setRequestHeader('Content-Type', 'application/json');
				//xhr.setRequestHeader('Authorization', token);
				return checkUserName(username);
			},
			success:function(result){
				if($.isFunction(registSuccess)){
					registSuccess(result);
				}
			},
			error:registError
		});
		
	});
	
	$("#logout").click(function(){
		logout();
	});
	

	
});


/***
 * 
 * @param username
 * @returns {Boolean}
 */
function checkUserName(username){
	var b = false;
	$.ajax({ // check whether the userName has already existed
		type:"post",
		async:false,
		url: basePath + "user/checkUserName/"+username, 
		dataType : "json",
		contentType : "application/json",
		beforeSend: function(xhr){xhr.setRequestHeader('Content-Type', 'application/json');},
		success: function(result){
	        if(result.status == -2){
	        	get('warning_1').style.display='block';
	        	b= false;
	        }else{
	        	b=true;
	        }
        },
        error:function(){
        	b=false;
        }
	});
	return b;
};


function logout(){
	var userId = getCookie("duke_cloud_note_user");
	delCookie("duke_cloud_note_user");//delete user
	delCookie(userId+"_token");//delete token
	location.href="log_in.html";
}

/**
 * check old password validity
 * @param oldPwd
 * @returns {Boolean}
 */
function validOldPwd(oldPwd){
	var flog = false;
	var loginUserName = getCookie(UserName);
       
	jQuery.support.cors = true;
	$.ajax({
		type:"post",
		async:false,
		url:basePath + "user/checkUserOldpwd/"+loginUserName+"/"+oldPwd,
		dataType : "json",
		beforeSend: function(xhr){
			xhr.setRequestHeader('Content-Type', 'application/json');
		},
		success:function(result){
			if(result.status == 1){
				flog = true;
			}
		}
	});
	return flog;
}


/**
 * chaneg pwd
 * @param changepwdSuccess
 * @param changepwdError
 */
function changepwd(changepwdSuccess,changepwdError){
	var loginUserId = getCookie(cookie_key);
	var pwd=$("#new_password").val();
	$.ajax({
		type:"post",
		url: basePath + "user/resetPwd/"+loginUserId+"/"+pwd, 
		dataType : "json",
		contentType : "application/json",
		beforeSend: function(xhr){
			var token=getCookie(loginUserId+"_token");
			xhr.setRequestHeader('Content-Type', 'application/json');
			xhr.setRequestHeader('Authorization', token);
		},
		success: function(result){
			changepwdSuccess(result);
        },
        error:function(){
        	changepwdError();
        }
	});
}



