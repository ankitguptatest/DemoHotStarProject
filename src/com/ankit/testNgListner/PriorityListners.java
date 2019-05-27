package com.ankit.testNgListner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;


import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class PriorityListners implements IMethodInterceptor{

	@Override
	public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
		Comparator<IMethodInstance> comparator = new Comparator<IMethodInstance>() {
	        private int getLineNo(IMethodInstance mi) {
	        int result = 0;

	        String methodName = mi.getMethod().getConstructorOrMethod().getMethod().getName();
	        String className  = mi.getMethod().getConstructorOrMethod().getDeclaringClass().getCanonicalName();
	        ClassPool pool    = ClassPool.getDefault();

	        try {
	            CtClass cc        = pool.get(className);
	            CtMethod ctMethod = cc.getDeclaredMethod(methodName);
	            result            = ctMethod.getMethodInfo().getLineNumber(0);
	        } catch (NotFoundException e) {
//	            e.printStackTrace();
	        }

	        return result;
	        }

	        public int compare(IMethodInstance m1, IMethodInstance m2) {
	        return getLineNo(m1) - getLineNo(m2);
	        }
	    };

	    IMethodInstance[] array = methods.toArray(new IMethodInstance[methods.size()]);
	    Arrays.sort(array, comparator);
	    return Arrays.asList(array);
		
		
//		List<IMethodInstance> result = new ArrayList<IMethodInstance>();
//		for (IMethodInstance method : methods) {
//			Test testMethod = method.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class);
//			if (testMethod.priority() == 1) {
//				result.add(method);
//			}
//		}
//		return result;
	}

}
