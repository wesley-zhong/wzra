package com.wd.erp.zra;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wd.erp.zra.bean.config.ZraConfig;

@Component
public class WdRpcService {

	@Inject
	private ZraConfig zraConfig;
	
	@Inject
	private DataSource dataSource;
	
	private Logger logger = LoggerFactory.getLogger(WdRpcService.class);

	public void sendRpcData() {
		String loginAccessToken = getHttpsToken();
		if (loginAccessToken == null)
			return;
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(zraConfig.getDataServiceUrl());
		post.addHeader(new BasicHeader("Authorization", "OAuth "+ loginAccessToken));

		try {
			String bodyData = "";
			StringEntity body = new StringEntity(bodyData);
			body.setContentType("application/json");
			post.setEntity(body);
			HttpResponse response1 = httpclient.execute(post);
			int statusCode1 = response1.getStatusLine().getStatusCode();

			// 200， call CaseRestFul sucess
			if (statusCode1 == 200) {
				logger.info("Insertion successful.");
				String response_string = EntityUtils.toString(response1
						.getEntity());
				/*
				 * get json，contains two keys， resultStatus 0: sucess,other
				 * failed resultMessage
				 */
				logger.info(response_string);
			} else {
				logger.info("Insertion unsuccessful. Status code returned is{} ",statusCode1);
				String response_string = EntityUtils.toString(response1
						.getEntity());
				/*
				 * return json，contain two keys， errorCode Code message
				 */
				logger.info(response_string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getHttpsToken() {

		HttpClient httpclient = HttpClientBuilder.create().build();
		String loginURL = zraConfig.getLoginUrl() + zraConfig.getGrantService()
				+ "&client_id=" + zraConfig.getClientId() + "&client_secret="
				+ zraConfig.getClientSecret() + "&password="
				+ zraConfig.getPassword();
		logger.info(loginURL);

		HttpPost httpPost = new HttpPost(loginURL);
		HttpResponse response = null;
		try {
			// Execute the login POST request
			response = httpclient.execute(httpPost);
		} catch (ClientProtocolException cpException) {
			cpException.printStackTrace();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		// if login failed
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			logger.info("Error authenticating to Force.com: "
					+ statusCode);
			// Error is in EntityUtils.toString(response.getEntity())
			return null;
		}

		// get reponse
		String getResult = null;
		try {
			getResult = EntityUtils.toString(response.getEntity());
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		JSONObject jsonObject = null;
		String loginAccessToken = null;
		try {
			jsonObject = (JSONObject) new JSONTokener(getResult).nextValue();
			loginAccessToken = jsonObject.getString("access_token");
			// loginInstanceUrl = jsonObject.getString("instance_url");
		} catch (JSONException jsonException) {
			jsonException.printStackTrace();
		}
		logger.info("token = {}" , loginAccessToken);
		return loginAccessToken;
	}

	public static void main(String[] args) {

	}
}
