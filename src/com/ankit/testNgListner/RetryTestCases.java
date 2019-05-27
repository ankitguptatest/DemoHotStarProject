package com.ankit.testNgListner;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryTestCases implements IRetryAnalyzer{

	
	private int retryCount = 0;
    private int maxCount = 1;
	
	public boolean retry(ITestResult result) {
        if (retryCount < maxCount){
        	System.out.println("Retrying again: ");
        	retryCount++;
        	return true;
        }
		return false;
	}
}
