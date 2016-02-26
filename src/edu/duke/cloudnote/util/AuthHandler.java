package edu.duke.cloudnote.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthHandler extends HandlerInterceptorAdapter {

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
	}


	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object object, ModelAndView mv)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object object) throws Exception {
		// "Basic DGFSRED%$E%%R^&TDGGFDRES"
		// "DGFSRED%$E%%R^&TDGGFDRES"
		// "username:token"
		String header = request.getHeader("Authorization");
		if (StringUtils.isNotEmpty(header)) {
			String[] headers = header.split(" ");
			if (headers.length == 2) {
				String con[] = new String (Base64.decodeBase64(headers[1].getBytes("utf-8"))).split(":");
//				String[] dec = Base64.decodeBase64(headers[1]).toString()
//						.split(":");
				if (con.length == 2) {
					String token = (String) request.getSession().getAttribute("userToken");
					// get token from session
					if (StringUtils.isNotEmpty(token)) {
						if (con[1].equals(token)) {
							return true;
						}
					}
				}
			}
		}

		response.setStatus(401);
		String result = "{'status':-1,'message':'tokens not match'}";
		response.getWriter().write(result);

		return false;
	}


}
