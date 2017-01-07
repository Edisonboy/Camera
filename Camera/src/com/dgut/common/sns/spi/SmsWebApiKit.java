package com.dgut.common.sns.spi;


import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dgut.common.sns.utils.MobClient;


/**
 * 服务端发起验证请求验证移动端(手机)发送的短信
 * @author Administrator
 *
 */
public class SmsWebApiKit {
	
	private static String appkey = "125a3281809ce";
	
	public SmsWebApiKit(String appkey) {
		super();
		this.appkey = appkey;
	}

	/**
	 * 服务端发起发送短信请求
	 * @return
	 * @throws Exception
	 */
	public  String sendMsg(String phone,String zone) throws Exception{
		
		String address = "https://webapi.sms.mob.com/sms/verify";
		MobClient client = null;
		try {
			client = new MobClient(address);
			client.addParam("appkey", appkey).addParam("phone", phone)
					.addParam("zone", zone);
			client.addRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			client.addRequestProperty("Accept", "application/json");
			String result = client.post();
			return result;
		} finally {
			client.release();
		}
	}
	
	/**
	 * 服务端发验证服务端发送的短信
	 * @return
	 * @throws Exception
	 */
	public static String checkcode(String phone,String zone,String code) throws Exception{
		
		String address = "https://webapi.sms.mob.com/sms/verify";
		MobClient client = null;
		try {
			client = new MobClient(address);
			client.addParam("appkey", appkey).addParam("phone", phone)
					.addParam("zone", zone).addParam("code", code);
			client.addRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			client.addRequestProperty("Accept", "application/json");
			String result = client.post();
			Map<String, Integer> map = JSONObject.fromObject(result);
			return map.get("status")+"";
//			return result;
		} finally {
			client.release();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		System.out.println(SmsWebApiKit.checkcode("134169525362", "86", "1234"));
		
	}
	
	
	

}
