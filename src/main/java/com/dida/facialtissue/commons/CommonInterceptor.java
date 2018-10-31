package com.dida.facialtissue.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:通用拦截器
 * @projectName:staff-development
 * @className:CommonInterceptor.java
 * @author:wentao
 * @createTime:2018年6月8日 下午4:25:49
 * @version 1.0.1
 */
public class CommonInterceptor implements HandlerInterceptor {

	private final String TOKEN = "token";
	
	@Autowired
	private RedisTemplateHelper redisTemplateHelper;

	/**
	 * @description:拦截请求
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * @author:
	 * @createTime:2018年6月8日 下午4:26:13
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

//		 // 通过请求cookie中的token去redis中获取用户信息
//		// String token = getToken(request, TOKEN);
//		
//		// if(token == null) {
//		// 	return false;
//		// }
//		
//		// //判断缓存中是否有用户信息
//		// String userInfo = redisTemplateHelper.getValue(token);
//		
//		// if(userInfo == null) {
//		// 	return false;
//		// }
		
		//request.getSession().setAttribute(Constants.USER_LOGIN_, userInfo);
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	private String getToken(HttpServletRequest request,String str) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (str.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}
}
