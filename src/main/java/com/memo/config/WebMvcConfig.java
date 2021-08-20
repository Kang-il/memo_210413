package com.memo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//images/** ::: images 의 하위 디렉터리 모두
		registry.addResourceHandler("/images/**")// http://localhost/images/---우리가 아까 만들었던 유일한 폴더 -- 접근 가능하게 매핑해주는 역할을 해준다.
		//우리파일이 어디에 있는지?
		.addResourceLocations("file:///D:\\1\\06_Spring_Project\\ex\\ExProjectWorkSpace\\Memo\\images/"); //실제 파일 저장 위치
	}
}
