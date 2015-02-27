package com.parkmycar.test;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.parkmycar.json.UserFeedbackJSONObj;
import com.parkmycar.model.enumeration.UserFeedbackType;

public class UserFeedbackServletTest 
{
	
	private static String BASE_SERVER_URL = "localhost";
	private static int SERVER_PORT = 8080;
	private static String PROTOCOL = "http";
	public static String PARKING_LOCATIONS_CPATH = "/ParkMyCarWebApp/UserFeedback";
	public static URI getFullUrl (String contextPath) {
		try {
			URI uri = new URI (PROTOCOL, null, BASE_SERVER_URL, SERVER_PORT, contextPath, null, null);
			return uri;
		} catch (URISyntaxException e) 
		{
		}
		return null;
	}
	public static void main (String args []) {
		Gson gson = new Gson();
		HttpClient httpClient = new DefaultHttpClient();			
		HttpPost httpPost = new HttpPost(getFullUrl(PARKING_LOCATIONS_CPATH));		
		UserFeedbackJSONObj jObj = new UserFeedbackJSONObj();
		jObj.setMcdid("3873ashd2");
		jObj.setpId(7);
		jObj.setTimetamp(new Date().getTime());
		jObj.setType(UserFeedbackType.AVAILABLE);
		try
		{
			//add data
			httpPost.setEntity(new StringEntity(gson.toJson(jObj)));
			// Execute HTTP Post Request
			HttpResponse response = httpClient.execute(httpPost);	
			
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				System.out.print("Success");			
			} else {
				System.out.print(response.getStatusLine().getStatusCode());
			}				        
		}
		catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
