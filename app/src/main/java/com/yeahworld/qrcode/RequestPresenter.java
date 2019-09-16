package com.yeahworld.qrcode;

import android.content.Context;
import android.os.Build;

import com.yeahworld.callback.RequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Byron
 */
public class RequestPresenter {

	private static String access_token = "1322FmhOhNV-MQ0tt1VpGiQuT0XevG*pA6hUVmuI0q5T2V2zQb5Y-Bg8OW-ybByNXugsux8d5Ujv-RBlu85ROZQdsDGMVZp1K89lW6p4EA0*kYPrgU7lVtATS4R9iiC5TxY8eIKcaSJdvRAYpRP1I6*IxFpkPImtckxmGhjudSeYFmRL*QH2bhpzCl1N0bXBulVgqADPdBN8M6HwWmYxVrDl4NliBrTXQOEfFUrYFE2*R6R5XO5WCCJBtWwUrbhfeLc*AlPh6W6D6D5uwQHNNvQw72Ph5LFFc6n4EU6k2uGNbBT9ZDmTEqSek0BH8pOCNvrrKD1RNY0PFg";
	private static String URL = "http://games.yeahworld.com/api/sdk/payment/crtorder?access_token=" + access_token;

	public String Request(final RequestCallback callback) throws IOException, JSONException {

		Map<String, String> param = new HashMap<>();
		param.put("trade_type","wechat");
		param.put("username","huohuohuo6");
		param.put("platform","yeahandroid10010");
		param.put("sid","1");
		param.put("rolename","向经赋");
		param.put("role_id","45301");
		param.put("subject","99元宝");
		param.put("order_type","1");
		param.put("attach","0");
		param.put("amount", "6.00");
		param.put("gateway", "APP");

		StringBuilder stringBuilder = new StringBuilder();
		if (param != null){

		}


		NetworkRequest networkRequest = new NetworkRequest();
		String data = networkRequest.sendParam(URL, param).body().string();
		System.out.println("Byron:  " + data);
		//做判断是否成功获取数据
		JSONObject orderParam = new JSONObject(data);
		int error_code = orderParam.optInt("error_code");
		if (error_code == 200){
			callback.successful(data);
		} else {
			callback.failure(data);
		}
		return data;
	}



}
