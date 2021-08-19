package com.memo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.interceptor.PermissionInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Autowired
	private PermissionInterceptor permissionInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(permissionInterceptor)
		.addPathPatterns("/**")// ** 아래 디렉토리까지 확인!!
		.excludePathPatterns("/user/sign_out","/static/**","/error"); 
		// excludePathPatterns 에 들어가는 URL은 Interceptor 을 타지 않는다. 
	}
}
