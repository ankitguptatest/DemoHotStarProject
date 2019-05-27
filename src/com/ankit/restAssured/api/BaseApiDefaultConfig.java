package com.ankit.restAssured.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ankit.JavaUtility.MachineSearch;
import com.ankit.Selenium.TestSuites;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.Configuration;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;
import static com.jayway.jsonpath.JsonPath.using;
import static com.jayway.jsonpath.Option.*;

public class BaseApiDefaultConfig {
	
	
	private Map<String,Object> requestParameters = new HashMap<String,Object>();
	public Map<String,Object> requestHeaders = new HashMap<String,Object>();
	private Map<String,String> requestCookies = new HashMap<String,String>();
	private Map<String, String> queryParameters = new HashMap<String,String>();
	private Map<String, String> pathParameters = new HashMap<String,String>();
	JSONObject json = new JSONObject();
	String requestBody = "";
 
	
	
	public Response getResponse(String requestType, String url, int statusCode){
		RequestSpecification rs = getRequestSpecification();
		String apiDetails = "Url = "+url+ "\nRequest Parameters = "+ requestParameters +"\nRequest Type = "+requestType+json.toString() + "\n Request Headers = "+requestHeaders;
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
		String apiDetails = "Url = <b>"+url+ "?\nQueryParam"+queryParameters+" </b>"+"\nPathParam: "+pathParameters+"\nRequest Parameters = "+ requestParameters +"\nJson Request="+json.toString()+requestBody+"\nRequest Type = "+requestType+"\n Request Headers = "+requestHeaders;
		System.out.println("\n"+apiDetails);
		Response response = null;
		try{
			response = rs.request(requestType, url);
			long time = response.getTime();
			System.out.println("-----"+response.getStatusCode());
			//System.out.println(response.asString());
			//System.out.println(response.getBody().asString());
			String json_String_to_print = response.getBody().asString();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
//			System.out.println(gson.toJson(jp.parse(json_String_to_print)));
			if (!(response.statusCode() == statusCode)){
				ts.getTestReporting().addApiTestSteps(ts, apiDetails, response, "FAIL");
				throw new Exception("Status code is not  "+statusCode);
			}
			else{
				ts.getTestReporting().addApiTestSteps(ts, apiDetails, response, "PASS");
			}
		}catch(Exception e){
//			e.printStackTrace();
			ts.getTestReporting().addApiTestSteps(ts,e.getMessage(), apiDetails,  "FAIL");
			throw new Exception("failures came");
		}
		ts.apiClassObj = new BaseApiDefaultConfig();
		return response;
	}
	
	
	public void validateResponse(TestSuites ts,String request, Response response,int statusCode) throws Exception {
		try{
			long time = response.getTime();
			System.out.println("-----"+response.getStatusCode());
			//			System.out.println(response.asString());
			//			System.out.println(response.getBody().asString());
			String json_String_to_print = response.getBody().asString();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
//			System.out.println(gson.toJson(jp.parse(json_String_to_print)));
			if (!(response.statusCode() == statusCode)){
				ts.getTestReporting().addApiTestSteps(ts, request, response, "FAIL");
				throw new Exception("Status code is not  "+statusCode);
			}
			else{
				ts.getTestReporting().addApiTestSteps(ts, request, response, "PASS");
			}
		}catch(Exception e){
//			e.printStackTrace();
			ts.getTestReporting().addApiTestSteps(ts,e.getMessage(), request,  "FAIL");
			throw new Exception("failures came");
		}
		ts.apiClassObj = new BaseApiDefaultConfig();
	}
	
	
	public Response getResponse(TestSuites ts, String requestType, String url, String payloadFileName, int statusCode) throws Exception{
		RequestSpecification rs = getRequestSpecification(payloadFileName);
		String apiDetails = "Url = "+url+ "?"+queryParameters+ "\nRequest Parameters = "+ requestParameters +"\nRequest Type = "+requestType+"\n Request Headers = "+requestHeaders;
		System.out.println(apiDetails);
		Response response = null;
		try{
			response = rs.request(requestType, url);
			long time = response.getTime();
			System.out.println("-----"+response.getStatusCode());
			//			System.out.println(response.asString());
			//			System.out.println(response.getBody().asString());
			String json_String_to_print = response.getBody().asString();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
//			System.out.println(gson.toJson(jp.parse(json_String_to_print)));
			if (!(response.statusCode() == statusCode)){
				ts.getTestReporting().addApiTestSteps(ts, apiDetails, response, "FAIL");
				throw new Exception("Status code is not  "+statusCode);
			}
			else{
				ts.getTestReporting().addApiTestSteps(ts, apiDetails, response, "PASS");
			}
		}catch(Exception e){
			e.printStackTrace();
			ts.getTestReporting().addApiTestSteps(ts,e.getMessage(), apiDetails,  "FAIL");
			System.out.println("failed");
			throw new Exception("failures came");
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
	
	public String jsonToURLEncoding(JSONObject json) {
	    String output = "";
	    String[] keys = JSONObject.getNames(json);
	    for (String currKey : keys)
	        output += jsonToURLEncodingAux(json.get(currKey), currKey);

	    return output.substring(0, output.length()-1);
	}

	private static String jsonToURLEncodingAux(Object json, String prefix) {
	    String output = "";
	    if (json instanceof JSONObject) {
	        JSONObject obj = (JSONObject)json;
	        String[] keys = JSONObject.getNames(obj);
	        for (String currKey : keys) {
	            String subPrefix = prefix + "[" + currKey + "]";
	            output += jsonToURLEncodingAux(obj.get(currKey), subPrefix);
	        }
	    } else if (json instanceof JSONArray) {
	        JSONArray jsonArr = (JSONArray) json;
	        int arrLen = jsonArr.length();

	        for (int i = 0; i < arrLen; i++) {
	            String subPrefix = prefix + "[" + i + "]";
	            Object child = jsonArr.get(i);
	            output += jsonToURLEncodingAux(child, subPrefix);
	        }
	    } else {

	    	output = prefix + "=" + json.toString() + "&";
	    }
	    return output;
	}
	
	
	/**
	 * It would return the value from the response body of the key
	 * @param ts
	 * @param r : response
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String getValueFromJson(TestSuites ts, Response r, String key) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		String value = "";
		try {
		JsonPath js = responseInJson(r);
		value = js.getString(key);
		ts.getTestReporting().addApiTestSteps(ts, method, "API key: "+key+", value: "+value, "PASS");
		System.out.println("API key: "+key+" value: "+value);
		}catch(Exception e) {
			ts.getTestReporting().addApiTestSteps(ts, method, "Excecption came in fetching data: API key: "+key+", value: "+value+ "Exception is:"+e.getMessage(), "FAIL");
		}
		return value;
	}
	
	
	public List<String> getAllValuesOfAnyKey(TestSuites ts, Response response, String url, String key) throws Exception{
		int arraySize = getArraySizeViaUrl(ts, response, url);
		List<String> list = new ArrayList<>();
		for(int i = 0; i <arraySize; i++) {
			list.add(getvalueThroughUrl(ts, response, url+"/["+i+"]/"+key));
		}
		return list;
	}
	
	
	
	/**
	 * Method would fail if the expected value from is not present in response body
	 * @param ts
	 * @param r
	 * @param key
	 * @param expectedData
	 * @throws Exception
	 */
	public void validateValueFromJson(TestSuites ts, Response r, String key, String expectedData) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		String actualData = getValueFromJson(ts, r, key);
		actualData = String.valueOf(actualData);
		if(expectedData.equalsIgnoreCase(actualData)){
			ts.getTestReporting().addApiTestSteps(ts, method, "API key: "+key+", value: "+actualData+" expected data is: "+expectedData, "PASS");
//			ts.getTestReporting().addApiTestSteps(ts, key, "Actual value: "+actualData+", Expected value: "+expectedData, "PASS");	
		}
		else{
//			ts.getTestReporting().addApiTestSteps(ts, key, "Actual value: "+actualData+", <b>Expected value: "+expectedData+"</b>", "FAIL");
			ts.getTestReporting().addApiTestSteps(ts, method, "API key: <b>"+key+", value: "+actualData+" expected data is: "+expectedData+"</b>", "FAIL");
		}
	}
	
	
	public void validateString(TestSuites ts,String actual, String expected) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		if(actual.equalsIgnoreCase(expected)) {
			ts.getTestReporting().addApiTestSteps(ts, method, "Actual value is: "+actual+", Expected value is: "+expected, "PASS");
		}
		else {
			ts.getTestReporting().addApiTestSteps(ts, method, "Actual value is: "+actual+", Expected value is: "+expected, "FAIL");	
		}
	}
	
	
	
	
	
	
	/**
	 * It would return the values which are present in array 
	 * @param ts
	 * @param r : responses
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List<Object> getListFromJson(TestSuites ts, Response r, String key) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		List<Object> allValues = new ArrayList<>();
		try {
		JsonPath js = responseInJson(r);
		allValues = js.getList(key);
		System.out.println("API key: "+key+" value: "+allValues);
		ts.getTestReporting().addApiTestSteps(ts, method, "API key: "+key+", value: "+allValues, "PASS");
		}catch(Exception e) {
			ts.getTestReporting().addApiTestSteps(ts, method, "Excecption came in fetching data: API key: "+key+ "Exception is:"+e.getMessage(), "FAIL");
		}
		return allValues;
	}
	
	
	public JSONArray addvalueInJsonArrayfromList(JSONArray array, List<String> list) {
		for(int i =0; i <list.size(); i++) {
			array.put(i, list.get(i));
		}
		return array;
	}
	
	
	
	/**
	 * It would return the json object from the response.
	 * If json is not start from "{" and start with "[" then in that case
	 * Method will add the "{ Ankit :" in the json body and create a json response so that
	 * Traversing is very easy
	 * @param r
	 * @return
	 */
	public JSONObject getJsonObject(Response r) { 
		JSONObject jsonObject = null;
		String responseData = r.getBody().asString();
		if(responseData.charAt(0) == '[') {
			responseData = "{ \"Ankit\":"+responseData+"}";
		}
		jsonObject = new JSONObject(responseData);
		return jsonObject;
	}
	
	
	private String[] getXpathOrder(String url) {
		String[] xpathOrder =  null;
		if(url.contains("&")) {
			xpathOrder = url.split("&");
		}
		else {
		xpathOrder = url.split("/");
		}
		return xpathOrder;
	}
	
	
	/**
	 * Url: pass url like:  $.releases.[0].release_key
	 * @param ts
	 * @param response
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	public String getValueThrowURl(TestSuites ts, Response response, String url) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		
		String json_String_to_print = response.getBody().asString();
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(json_String_to_print);
		String value = com.jayway.jsonpath.JsonPath.read(document, url);
		ts.getTestReporting().addApiTestSteps(ts, method, "API : "+url+", value: "+value, "PASS");
		return value;
	}
	
	
	/**
	 * Url: pass url like:  $.releases.[0].release_key
	 * @param ts
	 * @param response
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	public String getJsonValueThrowURl(TestSuites ts, String json, String url) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
		Object object = com.jayway.jsonpath.JsonPath.read(document, url);
		ts.getTestReporting().addApiTestSteps(ts, method, "API : "+url+", value: "+object, "PASS");
		return ""+object;
	}
	
	
	
	
	/**
	 * It would return the value from the json based on passed string url
	 * in the format of "/" or "&"
	 * @param ts
	 * @param r : response
	 * @param url : 
	 * @return
	 * @throws Exception
	 */
	public String getvalueThroughUrl(TestSuites ts, Response response, String url) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		String value = "";
		String[] xpathOrder =  getXpathOrder(url);
		JsonNode rootNode = null;
		String key = "";
		try {
		ObjectMapper objectMapper = new ObjectMapper();
		JSONObject jsonObject = getJsonObject(response);
		byte[] jsonData = jsonObject.toString().getBytes();
//		JsonNode rootNode = objectMapper.readTree(jsonData);
//		rootNode = objectMapper.readTree(jsonData);
		rootNode = objectMapper.readTree(jsonObject.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
		JsonNode node = null;
		for(int i=1;i<xpathOrder.length;i++) {
			if(node==null)
				node = rootNode;
			if(xpathOrder[i].contains("[")){
				xpathOrder[i] = xpathOrder[i].replace("[", "");
				xpathOrder[i] = xpathOrder[i].replace("]", "");
				node = node.get(Integer.parseInt(xpathOrder[i]));
			}
			else
				node = node.path(xpathOrder[i]);
			    key = xpathOrder[i];
		}
		value = node.asText();
		}catch(Exception e) {
			e.printStackTrace();
		}
		ts.getTestReporting().addApiTestSteps(ts, method, "API key: "+key+", value: "+value, "PASS");
		System.out.println("API key: "+key+" value is:"+value);
		return value;
	}
	
	
	public List<JsonNode> getAllArrayNodes(TestSuites ts, Response response, String url) throws Exception {
		int size = getArraySizeViaUrl(ts, response, url);
		List<JsonNode> list = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		JSONObject jsonObject = getJsonObject(response);
		JsonNode rootNode = objectMapper.readTree(jsonObject.toString());
		rootNode = getArrayNode(ts, response, url);
		for(int i =0; i <size; i++) {
			JsonNode node = rootNode.get(i);
			list.add(node);
		}
      return list;		
	}
	
	

	public int getArrayIndexOfValue(TestSuites ts, Response r, String url, String key, String expectedValue) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		JsonNode rootNode =getArrayNode(ts, r, url);
		JsonNode node = null;
		String value = "";
		int index = 1000;
		for(int i =0; i<rootNode.size(); i++) {
			if(node == null) {
				node =  rootNode;
			}
			try {
			node = rootNode.get(i);
			node = node.path(key);
		    value = node.asText();
			}catch(Exception e) {
				e.printStackTrace();
			}
			if(value.equalsIgnoreCase(expectedValue)) {
				index = i;
				break;
			}
		}
		if(index == 1000)
		ts.getTestReporting().addApiTestSteps(ts, method, "Unable to find the index for value: "+expectedValue+" of key: "+key, "PASS");
		return index;
	}
	
	
	private JsonNode getArrayNode(TestSuites ts, Response r, String url) throws JsonProcessingException, IOException {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		String[] xpathOrder =  getXpathOrder(url);
		JsonNode rootNode = null;
		JsonNode node = null;
		try {
		ObjectMapper objectMapper = new ObjectMapper();
		JSONObject jsonObject = getJsonObject(r);
		byte[] jsonData = jsonObject.toString().getBytes();
//		JsonNode rootNode = objectMapper.readTree(jsonData);
//		rootNode = objectMapper.readTree(jsonData);
		rootNode = objectMapper.readTree(jsonObject.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
		for(int i=1;i<xpathOrder.length;i++) {
			if(node==null)
				node = rootNode;
			if(xpathOrder[i].contains("[")){
				xpathOrder[i] = xpathOrder[i].replace("[", "");
				xpathOrder[i] = xpathOrder[i].replace("]", "");
//				JsonNode childNode = node;
//				for(int j = 0; j<childNode.size(); j++) {
				node = node.get(Integer.parseInt(xpathOrder[i]));
//				}
			}
			else
				node = node.path(xpathOrder[i]);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return node;
	}
	
	
	
	
	
	public int getArraySizeViaUrl(TestSuites ts, Response response,String parameterName) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		int value = 0;
		//String parameterXPath = relevantRequestParameters.getProperty("ResponseKey_"+parameterName);
		String[] xpathOrder = getXpathOrder(parameterName);
		JsonNode rootNode = null;
		try {
		ObjectMapper objectMapper = new ObjectMapper();
		JSONObject jsonObject = getJsonObject(response);
		byte[] jsonData = jsonObject.toString().getBytes();
//		JsonNode rootNode = objectMapper.readTree(jsonData);
//		rootNode = objectMapper.readTree(jsonData);
		rootNode = objectMapper.readTree(jsonObject.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		JsonNode node = null;
		for(int i=1;i<xpathOrder.length;i++) {
			if(node==null)
				node = rootNode;
			if(xpathOrder[i].contains("[")){
				xpathOrder[i] = xpathOrder[i].replace("[", "");
				xpathOrder[i] = xpathOrder[i].replace("]", "");
				node = node.get(Integer.parseInt(xpathOrder[i]));
			}
			else
				node = node.path(xpathOrder[i]);
		}
		value = node.size();
		ts.getTestReporting().addApiTestSteps(ts, method, "Size of : "+parameterName+", value: "+value, "PASS");
		return value;
	}
	
	
	
	public RequestSpecification getRequestSpecification(){
		RequestSpecification rs = null;
		try{
		requestHeaders.put("Accept","application/json");
		requestHeaders.put("Content-Type","application/json;charset=\"utf-8\"");
//		requestHeaders.put("Accept-Encoding","gzip");
//		requestHeaders.put("Accept-Encoding","application/json");
		RestAssured.config = RestAssured.config().
				 httpClient(HttpClientConfig.httpClientConfig().
                 setParam( "CONNECTION_MANAGER_TIMEOUT", 70000).
                 setParam( "SO_TIMEOUT", 70000)); 
//		requestHeaders.put("Accept-Encoding","gzip");
		 if(json.length() == 0 && requestBody.isEmpty()) {
		 rs = RestAssured.given().
				 config(config).
		         headers(requestHeaders).
		         body(requestParameters).
		         queryParams(queryParameters).
		         pathParams(pathParameters);
		 }
		 else if (!requestBody.isEmpty()) {
			 rs = RestAssured.given().
					 config(config).
			         headers(requestHeaders).
//			         body(requestParameters).
			         body(requestBody).
			         queryParams(queryParameters).
			         pathParams(pathParameters);
		 }
		 else {
			 rs = RestAssured.given().
					 config(config).
			         headers(requestHeaders).
			         body(requestParameters).
			         body(json.toString()).
			         queryParams(queryParameters).
			         pathParams(pathParameters);
				 
		 }
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	
	
	public RequestSpecification getRequestSpecification(String fileName){
		String dir = System.getProperty("user.dir")+File.separator+"src";
		RequestSpecification rs = null;
		try{
			String path = new MachineSearch().serachMachineForFile(dir,fileName);
		    String payload = new String(Files.readAllBytes(Paths.get(path)));
		requestHeaders.put("Accept","application/json");
		requestHeaders.put("Content-Type","application/json");
		 RestAssured.config = RestAssured.config().
				 httpClient(HttpClientConfig.httpClientConfig().
                 setParam( "CONNECTION_MANAGER_TIMEOUT", 30000).
                 setParam( "SO_TIMEOUT", 30000)); 
//		requestHeaders.put("Accept-Encoding","gzip");
		 rs = RestAssured.given().
				 config(config).
		         headers(requestHeaders).
		         body(payload).
//		         body(requestParameters).
		         queryParams(queryParameters).
		         pathParams(pathParameters); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	
	public void createQueryParam(String...param){
		RequestSpecification rs = getRequestSpecification();
		for (String request: param){
			String query = request.split("=")[0];
			String value = request.split("=")[1];
//			queryParameters.put(query, value);
			rs.queryParam(query, value);
		}
	}
	
	
	public void createQueryMap(String...param){
//		queryParameters = new HashMap<String,String>();
		for (String request: param){
			String query = request.split("=")[0];
			String value = request.split("=")[1];
			queryParameters.put(query, value);
		}
	}
	
	public void createHeaderMap(String...param){
		for (String request: param){
			String query = request.split("=")[0];
			String value = request.split("=")[1];
			requestHeaders.put(query, value);
		}
	}
	
	public void createPathParmMap(String...param){
//		pathParameters = new HashMap<String,String>();
		for (String request: param){
			String path = request.split("=")[0];
			String value = request.split("=")[1];
			pathParameters.put(path, value);
		}
	}
	
	public void createRequestPayLoad(String...param){
		for (String request: param){
			String payLoad = request.split("=")[0];
			String value = "";
			try {
			value = request.split("=")[1];
			}catch(Exception e) {
				
			}
			requestParameters.put(payLoad, value);
		}
	}
	
	public void addRequestBodyAsJson(JSONObject json1) {
		json = json1;
	}
	

	
	public void addRequestBodyAsString(String value) {
		requestBody = value;

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
