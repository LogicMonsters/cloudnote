/**
 * @param result
 */
function loginSuccess(result){
	if(result.status == 1){
		//userId is stored in cookie
		addCookie(cookie_key,result.resource.cnUserId,2);
		addCookie(UserName,result.resource.cnUserName,2);
		location.href="edit.html";
	}else{
		alert("sign in failed");
	}
	
}

function loginError(){
	alert("sign in exception");
}


/***
 * @param result
 */
function registSuccess(result){
	if(result.status == 1){
		alert("sign up success");
		get('zc').className='sig sig_out';
		get('dl').className='log log_in';
	}else{
		alert("sign up failed");
	}
}

function registError(){
	alert("sign up exception");
}




/***
 * @param result
 */
function changepwdSuccess(result){
	if(result.status == 1){
		alert("reset pwd success");
		logout();
	}else{
		alert("reset pwd failure");
	}
}

function changepwdError(){
	alert("system exception");
	location.href="log_in.html";
}