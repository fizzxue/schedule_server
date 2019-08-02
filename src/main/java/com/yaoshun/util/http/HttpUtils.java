package com.yaoshun.util.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author fizz
 * @since 2019/7/23 18:15
 */
@Slf4j
public class HttpUtils {

	private static RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
			.setConnectionRequestTimeout(3000).setSocketTimeout(10000).build();

	public static String reqPost(String iaddr, String iparam){
		if (StringUtils.isEmpty(iaddr) || StringUtils.isEmpty(iparam)) return null;
		try (CloseableHttpClient httpclient = HttpClients.createDefault()){
			HttpPost httpPost = new HttpPost(iaddr);
			log.info("Executing request " + httpPost.getRequestLine());
			StringEntity stringEntity = new StringEntity(iparam, ContentType.APPLICATION_JSON);
			httpPost.setEntity(stringEntity);
			httpPost.setConfig(HttpUtils.requestConfig);
			ResponseHandler<String> responseHandler = response -> {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			};
			return httpclient.execute(httpPost, responseHandler);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return e.getMessage();
		}
	}

	public static void main(String[] args) {
		log.info(HttpUtils.reqPost("http://220.160.53.4:40019/pushtools/pushOnce", "{\"tableId\":\"12\",\"catalogId\":\"WEB1334\",\"tableName\":\"GL_SUIDAOSHIBIEXX\",\"optype\":\"add\",\"ignoreField\":\"wetherunderwater\"}"));
//		log.info(HttpUtils.reqPost("http://127.0.0.1:8585/pushtools/pushOnce", "{\"tableId\":\"12\",\"catalogId\":\"WEB1334\",\"tableName\":\"GL_SUIDAOSHIBIEXX\",\"optype\":\"add\",\"ignoreField\":\"wetherunderwater\"}"));
	}
}
