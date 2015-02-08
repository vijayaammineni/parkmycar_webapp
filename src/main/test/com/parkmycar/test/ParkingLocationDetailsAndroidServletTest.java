package com.parkmycar.test;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class ParkingLocationDetailsAndroidServletTest 
{
	
	private static String BASE_SERVER_URL = "localhost";
	private static int SERVER_PORT = 8080;
	private static String PROTOCOL = "http";
	public static String PARKING_LOCATIONS_CPATH = "/ParkMyCarWebApp/ParkingLocationDetails";
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
		HttpClient httpClient = new DefaultHttpClient();			
		HttpPost httpPost = new HttpPost(getFullUrl(PARKING_LOCATIONS_CPATH));			
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		Integer id = 3;
		nameValuePairs.add(new BasicNameValuePair("id", id.toString()));
		
		try
		{
			//add data
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpClient.execute(httpPost);	
			
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				InputStream in = response.getEntity().getContent();					
				byte [] bytes = IOUtils.toByteArray(in);		
				System.out.print(new String(bytes));			
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
