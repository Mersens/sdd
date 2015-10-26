package com.shangdingdai.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpPostUtils {
	public static String doPost(String url, Map<String, String> map) {
		String results = null;
		HttpEntity httpentity = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				params.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}
		try {
			httpentity = new UrlEncodedFormEntity(params, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(httpentity);
		HttpResponse httpResponse = null;
		try {
			httpResponse = new DefaultHttpClient().execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			try {
				results = EntityUtils.toString(httpResponse.getEntity());
				return results;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return results;
	}

	public static String doPostImg(String url, Map<String, String> map,
			File file) {
		String results = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		FileBody fileBody = new FileBody(file);
		StringBody stringBody = null;
		try {
			stringBody = new StringBody(map.get("userid"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MultipartEntity entity = new MultipartEntity();
		entity.addPart("imgsrc", fileBody);
		entity.addPart("userid", stringBody);
		post.setEntity(entity);
		HttpResponse response = null;
		try {
			response = httpclient.execute(post);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			try {
				results = EntityUtils.toString(response.getEntity());
				return results;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return results;
	}

	public static String doHttpGet(String url, Map<String, String> map) {
		String results = null;
		StringBuffer sbf = new StringBuffer(url);
		sbf.append("?");
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				sbf.append(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		String path = sbf.toString();
		HttpGet httpGet = new HttpGet(path.substring(0, path.length() - 1));
		HttpResponse httpResponse = null;
		try {
			httpResponse = new DefaultHttpClient().execute(httpGet);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			try {

				results = EntityUtils.toString(httpResponse.getEntity());
				return results;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return results;
	}

	public static String doGetBanners(String url) {
		String results = null;
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = null;
		try {
			httpResponse = new DefaultHttpClient().execute(httpGet);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			try {
				results = EntityUtils.toString(httpResponse.getEntity());
				return results;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return results;
	}

}
