package com.memo.interceptor;

import javax.servlet.http.HttpServletRequest;	
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;



//@Compnent--부모 모든 Spring Bean을 아우르는 객체
//@Service @Controller @Repository -- 자식

@Component //Spring Bean으로써 -- 다른곳에서 @Autowired 사용가능
public class PermissionInterceptor implements HandlerInterceptor{
	
	//private Logger logger=LoggerFactory.getLogger(PermissionInterceptor.class());
	private Logger logger=LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) 
			throws Exception {
	
		HttpSession session = request.getSession();
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		//URL - url path 확인
		String uri = request.getRequestURI();
		
		//SLF4J ::: SimpleLogging Facade For Java
		//System.out.println -- > Thread Safe 하지 않기 때문에 메서드 수행중 사용자에게 Lock 이 걸릴 수도 있으므로 쓰면 안됨
		logger.info("preHandler:::::::::::::::::"+uri);

		if(userLoginId==null && uri.startsWith("/post")) {//1. 비 로그인 &&post =>로그인 페이지로 리다이렉트
			response.sendRedirect("/user/sign_in_view");
			return false;
		}
		
		//2. 로그인 && signUp In  ==> 포스트 리스트 페이지로 리다이렉트
		if(userLoginId != null &&uri.startsWith("/user")){
			response.sendRedirect("/post/post_list_view");
			return false;
		}
		
		return true;
	};
	

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String uri = request.getRequestURI();
		logger.info("postHandler:::::::::::::::::"+uri);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String uri = request.getRequestURI();
		logger.info("afterCompletion:::::::::::::::::"+uri);
	}
	
}
