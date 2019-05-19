package com.airport.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Httputil {
	public static final String BASE_URL = "http://192.168.1.103:8080/Tech.Airport_server/";

	public static HttpGet getHttpGet(String url) {
		HttpGet request = new HttpGet(url);
		return request;
	}

	public static HttpPost getHttpPost(String url) {
		HttpPost request = new HttpPost(url);
		return request;
	}

	public static HttpResponse getHttpResponse(HttpGet request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}

	public static HttpResponse getHttpResponse(HttpPost request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}

	public static String queryStringForPost(String url) {
		HttpPost request = Httputil.getHttpPost(url);
		request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		String result = null;
		try {
			HttpResponse response = Httputil.getHttpResponse(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "�����쳣";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "�����쳣";
			return result;
		}
		return null;
	}

	public static String queryStringForPost(HttpPost request) {
		String result = null;
		try {
			HttpResponse response = Httputil.getHttpResponse(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "�����쳣";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "�����쳣";
			return result;
		}
		return null;
	}

	public static String queryStringForGet(String url) {
		HttpGet request = Httputil.getHttpGet(url);
		String result = null;
		try {
			HttpResponse response = Httputil.getHttpResponse(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "�����쳣";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "�����쳣";
			return result;
		}
		return null;
	}
}
