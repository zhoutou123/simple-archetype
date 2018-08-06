package com.miya.aop;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSONObject;

@Aspect
@Configuration
public class RequestInterceptor {

	protected static ThreadLocal<Long> start = new ThreadLocal<Long>();

	private static final Logger _LOGGER = LoggerFactory.getLogger(RequestInterceptor.class.getSimpleName());

	/**
	 * 切面: controller包下所有公共方法
	 */
	@Pointcut("execution(public * com.miya.controller..*.*(..))")
	public void request() {
	}

	/**
	 * 前置通知
	 */
	@Before("request()")
	public void before() {
		start.set(System.currentTimeMillis());
	}

	/**
	 * @description 环绕通知
	 */
	@Around("request()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		Signature signature = pjp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		// Method method = methodSignature.getMethod();
		String methodName = signature.getName();
		String[] parNames = methodSignature.getParameterNames();
		JSONObject json = new JSONObject();
		Object[] pars = pjp.getArgs();
		// RequestAnnotation ao = method.getAnnotation(RequestAnnotation.class); //针对特定注解可做处理
		Object res = pjp.proceed();
		for (int i = 0; i < parNames.length; i++) {
			// 针对于请求参数为HttpServletRequest时增加解析逻辑
			if (pars[i] instanceof HttpServletRequest) {
				json = getRequestString((HttpServletRequest) pars[i]);
			} else {
				json.put(parNames[i], pars[i]);
			}
		}
		try {
			JSONObject resJson = (JSONObject) JSONObject.toJSON(res);
			_LOGGER.info("接口:{},接收信息:{};返回信息:{};请求耗时:{}ms", methodName, json.toString(), resJson, (System.currentTimeMillis() - start.get()));
		} catch (Exception e) {
			_LOGGER.error("返回信息解析异常,{}:{}", methodName, e.getMessage());
		}
		start.remove();
		return res;
	}

	private JSONObject getRequestString(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Enumeration<String> keys = request.getParameterNames();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			json.put(key, request.getParameter(key));
		}
		return json;
	}

}
