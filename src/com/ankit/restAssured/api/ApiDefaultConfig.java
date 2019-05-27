package com.ankit.restAssured.api;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import com.ankit.Selenium.TestSuites;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

public class ApiDefaultConfig {
	
	
	private Map<String,Object> requestParameters = new HashMap<String,Object>();
	private Map<String,Object> requestHeaders = new HashMap<String,Object>();
	private Map<String,String> requestCookies = new HashMap<String,String>();
	
	
	public Response getResponse(String requestType, String url, int statusCode){
		RequestSpecification rs = getRequestSpecification();
		String apiDetails = "Url = "+url+ "\nRequest Parameters = "+ requestParameters +"\nRequest Type = "+requestType+"\n Request Headers = "+requestHeaders;
		System.out.println(apiDetails);
		Response response = null;
		try{
		 response = rs.request(requestType, url);
		 long time = response.getTime();
//		Assert.assertEquals(response.getStatusCode(), statusCode);
//		Assert.assertEquals(response.getContentType(), ContentType.JSON);
//		Assert.assertEquals(response.getContentType(), "application/json");
//		 ValidatableResponse  log = response.then().log().all();
			System.out.println("-----");
//			System.out.println(response.asString());
//			System.out.println(response.getBody().asString());
			String json_String_to_print = response.getBody().asString();
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					JsonParser jp = new JsonParser();
					System.out.println(gson.toJson(jp.parse(json_String_to_print)));
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return response;
	}
	
	
	public Response getResponse(TestSuites ts, String requestType, String url, int statusCode) throws Exception{
		RequestSpecification rs = getRequestSpecification();
		String apiDetails = "Url = "+url+ "\nRequest Parameters = "+ requestParameters +"\nRequest Type = "+requestType+"\n Request Headers = "+requestHeaders;
		System.out.println(apiDetails);
		Response response = null;
		try{
			response = rs.request(requestType, url);
			long time = response.getTime();
			//		Assert.assertEquals(response.getStatusCode(), statusCode);
			//		Assert.assertEquals(response.getContentType(), ContentType.JSON);
			//		Assert.assertEquals(response.getContentType(), "application/json");
//			response.then().log().all();
			System.out.println("-----"+response.getStatusCode());
			//			System.out.println(response.asString());
			//			System.out.println(response.getBody().asString());
			String json_String_to_print = response.getBody().asString();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
//			System.out.println(gson.toJson(jp.parse(json_String_to_print)));
			if (json_String_to_print.isEmpty() || json_String_to_print == null || ! (response.statusCode() == statusCode)){
				ts.getTestReporting().addApiTestSteps(ts, apiDetails, response, "FAIL");
			}
			else{
				ts.getTestReporting().addApiTestSteps(ts, apiDetails, response, "PASS");
			}
		}catch(Exception e){
//			e.printStackTrace();
			ts.getTestReporting().addApiTestSteps(ts, apiDetails, response, "FAIL");
		}
		return response;
	}
	
	
	
	/**
	 * It will hit the passed url and return true if the status code is 200 otherwise return
	 * false if not.
	 * @param url
	 * @param statusCode
	 * @return
	 */
	public boolean checkgetRequest(String url, int statusCode){
		try{
		Response rs = given().
		get(url).
		then().extract().response();
		if (rs.statusCode() == statusCode){
		return true;
		}
		else{
			System.err.println(url);
			System.err.println(rs.statusCode());
			return false;
		}
		}catch(Exception e){
			System.err.println(url);
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) {
//		System.out.println(checkgetRequest("https://blogs.adobe.com/digitalmarketing/search-marketing/seo-for-success-in-video-marketing/"));
//		System.out.println(checkgetRequest("http://ir.chipotle.com/phoenix.zhtml?c=194775&p=irol-newsArticle&ID=2130375"));
//		System.out.println(checkgetRequest("https://d2bxpc4ajzxry0.cloudfront.net/TripAdvisorInsights/sites/default/files/styles/tamc_featured/public/28190_updating_payment_information.jpg?itok=qpCdutrB&c=5343ac42acf909a0cfd4d0acd86f4411"));
//		System.out.println(checkgetRequest("https://s3.amazonaws.com/blog4.0/blog/wp-content/uploads/2017/11/11-DON%E2%80%99T-let-bad-reviews-get-you-down-3-1024x181.jpg"));
		System.out.println(checkgetRequest("http://www.revlocal.com/FileStore.ashx?id=129972"));
//		System.out.println(checkgetRequest("https://ddjkm7nmu27lx.cloudfront.net/205840024/spring-cleaning-for-your-teeth (1).jpg"));
//		System.out.println(checkgetRequest("https://www.superpages.com/about/termsofuse.html"));
//		System.out.println(checkgetRequest("https://glip-vault-1.s3.amazonaws.com/web/customer_files/875920719884/modified.png?Expires=2075494478&AWSAccessKeyId=AKIAJROPQDFTIHBTLJJQ&Signature=UlsE7kI2kzfo%2B%2B5YCoQy6pjVwF8%3D"));
//		System.out.println(checkgetRequest("https://glip-vault-1.s3.amazonaws.com/web/customer_files/877062660108/modified.png?Expires=2075494478&AWSAccessKeyId=AKIAJROPQDFTIHBTLJJQ&Signature=Fb%2B65wHYo6hSNlO8dsEsrED3ZGg%3D"));
//		System.out.println(checkgetRequest("https://glip-vault-1.s3.amazonaws.com/web/customer_files/877062651916/modified.png?Expires=2075494478&AWSAccessKeyId=AKIAJROPQDFTIHBTLJJQ&Signature=hLCBafpIVc2QN00EmNpHG9v0cJc%3D"));
//		System.out.println(checkgetRequest("https://glip-vault-1.s3.amazonaws.com/web/customer_files/877064642572/modified.png?Expires=2075494478&AWSAccessKeyId=AKIAJROPQDFTIHBTLJJQ&Signature=TYLt8%2B%2F5BGjy75FsoM27Amxjv6I%3D"));
//		System.out.println(checkgetRequest("https://glip-vault-1.s3.amazonaws.com/web/customer_files/877066854412/modified.png?Expires=2075494478&AWSAccessKeyId=AKIAJROPQDFTIHBTLJJQ&Signature=mx5XpT29ftwGGAu%2F2ykobvNLFAk%3D"));
//		System.out.println(checkgetRequest("http://www.business2community.com/infographics/millennials-love-user-generated-content-infographic-01497502#plJ7csPRp05zwo5U.97"));
//		System.out.println(checkgetRequest("https://glip-vault-1.s3.amazonaws.com/web/customer_files/877062660108/modified.png?Expires=2075494478&AWSAccessKeyId=AKIAJROPQDFTIHBTLJJQ&Signature=Fb%2B65wHYo6hSNlO8dsEsrED3ZGg%3D"));
//		try {
//			
////			java.net.URL url = new java.net.URL("https://glip-vault-1.s3.amazonaws.com/web/customer_files/877062660108/modified.png?Expires=2075494478&AWSAccessKeyId=AKIAJROPQDFTIHBTLJJQ&Signature=Fb%2B65wHYo6hSNlO8dsEsrED3ZGg%3D");
////			System.out.println(checkgetRequest(url));
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
	}
	
	/**
	 * It will hit the passed url and return status code
	 * false if not.
	 * @param url
	 * @param statusCode
	 * @return
	 */
	public static int checkgetRequest(String url){
		int statusCode  = 0;
		try{
			// follow true means http and https both with work, url either will have http and redirect to https but it works
			
			RequestSpecification httpRequest = RestAssured.given().relaxedHTTPSValidation().redirects().follow(true).urlEncodingEnabled(true);
			//		        httpRequest.header("Content-Type", "application/json,text/html,application/xml,charset=UTF-8,text/plain,charset=utf-8,application/x-www-form-urlencoded");
			//		        httpRequest.header("accept-encoding", "gzip,deflate,br");
			//		        httpRequest.header("Accept", "text/html,application/xhtml+xml,application/xml,q=0.9,image/webp,image/apng,*/*;q");
			//			    httpRequest.header("Accept", "*/*");
			//			    httpRequest.header("accept-encoding", "*/*");
			//		        httpRequest.header("User-Agent", "Mozilla /5.0 (Compatible MSIE 9.0;Windows NT 6.1;WOW64; Trident/5.0)");
						     httpRequest.header("Content-Type", "application/x-www-form-urlencoded");

			httpRequest.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36");
			Response rs = httpRequest.get(url).then().extract().response();
			//		Response rs = given().
			//		relaxedHTTPSValidation().
			//		header("User-Agent", "Mozilla /5.0 (Compatible MSIE 9.0;Windows NT 6.1;WOW64; Trident/5.0)").
			//		get(url).
			//		then().extract().response();
			statusCode= rs.statusCode();
			if(statusCode != 200) {
//				throw new Exception("Try again");
			}
			//		if (rs.statusCode() == statusCode){
			//		return true;
			//		}
			//		else{
			//			System.err.println(url);
			//			System.err.println(rs.statusCode());
			//			return false;
			//		}
		}
		//		catch(SSLException sl) {
		//			sl.printStackTrace();
		//			return 200;
		//		}
		catch(Exception e){
//			System.err.println(url);
//			e.printStackTrace();
			try {
				RequestSpecification httpRequest = RestAssured.given().relaxedHTTPSValidation().redirects().follow(true).urlEncodingEnabled(false);
				httpRequest.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36");
				Response rs = httpRequest.get(url).then().extract().response();
				statusCode= rs.statusCode();
			}catch(Exception e1) {
//				System.err.println("finally failed : "+url);
//				e1.printStackTrace();
			}
		}
		return statusCode;
	}
	
	
	public XmlPath responseInXml(Response r){
		String response = r.asString();
		XmlPath xml = new XmlPath(response);
		return xml;
	}

	public JsonPath responseInJson(Response r){
		String response = r.asString();
		JsonPath js = new JsonPath(response);
		return js;
	}
	
	public String getValueFromJson(Response r, String key){
		JsonPath js = responseInJson(r);
		String value = js.getString(key);
		return value;
	}
	
	
	
	public RequestSpecification getRequestSpecification(){
		
		RequestSpecification rs = null;
		try{
//		requestHeaders.put("Accept","application/json");
		requestHeaders.put("Content-Type","application/json");
//		requestHeaders.put("Accept-Encoding","gzip");
		 rs = RestAssured.given().
		headers(requestHeaders).
		body(requestParameters);
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	
	public void addRequestBody(String headerKey, Object value ){
		requestParameters.put(headerKey, value);
	}
	
	public void addRequestHeaderValue(String headerKey, Object value ){
		requestHeaders.put(headerKey, value);
	}
	
	public void addRequestCookies(String key, String value ){
		requestCookies.put(key, value);
	}

}
