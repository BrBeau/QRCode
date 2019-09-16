package com.yeahworld.qrcode;

import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkRequest {

	private OkHttpClient okHttpClient = new OkHttpClient();

	/**
	 * OkHttp 网络请求获取订单号
	 *
	 * @param url 请求网址
	 * @param param 请求参数
	 * @return 返回Response体
	 * @throws IOException IO流异常
	 */
	public Response sendParam (String url, Map<String, String> param) throws IOException {

		//Stringbuilder字符串拼接
		StringBuilder stringBuilder = new StringBuilder();
		if (param != null){
			for (Map.Entry<String, String> entry : param.entrySet()){
				stringBuilder.append(entry.getKey());
				stringBuilder.append("=");
				stringBuilder.append(entry.getValue());
				stringBuilder.append("&");
			}
			stringBuilder.substring(0, stringBuilder.length() - 1);
		}
		MediaType mediaType =MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
		RequestBody requestBody = RequestBody.create(mediaType, stringBuilder.toString());
		Request request = new Request.Builder()
				.post(requestBody)
				.url(url)
				.build();
		return okHttpClient.newCall(request).execute();
	}



}
