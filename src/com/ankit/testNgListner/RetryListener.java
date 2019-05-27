package com.ankit.testNgListner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;


public class RetryListener implements IAnnotationTransformer {
	

	public void transform(ITestAnnotation testannotation, Class testClass, Constructor testConstructor, Method testMethod)	{
		IRetryAnalyzer retry = testannotation.getRetryAnalyzer();
		String name = testMethod.getDeclaringClass().getName();
//		System.out.println(name);
		if (retry == null){
			testannotation.setRetryAnalyzer(RetryTestCases.class);
		}
	}

}
