package com.ankit.WebPages;

import java.util.ArrayList;
import java.util.List;

import com.ankit.DataMaps.CommonDataMaps;
import com.ankit.Selenium.TestSuites;

public class HotStarHomePage extends Page{

	public List<String> getTray(TestSuites ts, int requiredTray) throws Exception {
		Thread.sleep(CommonDataMaps.waitConfigValues.get("second_5")); 
		scrollDown(ts, true);
		elementPath = "HotStar_HomePage/NumberOfTry";
		List<String> totalTray= getTextOfAllWebElement(ts, elementPath);
		List<String> requiredNumberOfTray = new ArrayList<String>();
		if(totalTray.size() >= requiredTray) {
		for(int i =0; i <requiredTray; i++) {
			requiredNumberOfTray.add(totalTray.get(i));
		}
		}
		scrollUp(ts, false);
		return requiredNumberOfTray;
	}
	
	public void login(TestSuites ts, String mobileNumber) throws Exception {
		elementPath = "HotStar_HomePage/Login_BT";
		click(ts, elementPath );
		elementPath = "HotStar_HomePage/Enter_Mobile";
		enterValue(ts, elementPath, mobileNumber);
		elementPath = "HotStar_HomePage/ContinueAfterEnteringMobileNum";
		click(ts, elementPath );
		System.err.println("Please enter OTP");
		Thread.sleep(CommonDataMaps.waitConfigValues.get("second_30"));
		elementPath = "HotStar_HomePage/UserProfile";
		verifyPresenceOfElement(ts, elementPath);
	}
	
	public void getDiffOfTrays(TestSuites ts, List<String> beforeLogin,  List<String> afterLogin) throws Exception {
		ts.getTestReporting().addTestSteps(ts,"Before Login Total Tray", ""+beforeLogin, "PASS",false);
		ts.getTestReporting().addTestSteps(ts,"After Login Total Tray", ""+beforeLogin, "PASS", false);
		for(int i = 0; i <beforeLogin.size(); i++) {
			String tray = beforeLogin.get(i);
			if(afterLogin.contains(tray)) {
				afterLogin.remove(tray);
			}
			else {
				ts.getTestReporting().addTestSteps(ts,"Missing Tray", tray, "PASS", false);		
			}
		}
		
		for(int i =0; i <afterLogin.size(); i++) {
			ts.getTestReporting().addTestSteps(ts,"Additional Tray", afterLogin.get(i), "PASS", false);
		}
	}
	
	
}

