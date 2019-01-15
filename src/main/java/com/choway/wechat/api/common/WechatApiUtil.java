package com.choway.wechat.api.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Slf4j
@Component
public class WechatApiUtil {

	public static String TOKEN;


	@Value("${wechat.message.token}")
	public void setToken(String token) {
		WechatApiUtil.TOKEN = token;
	}


	/** 验证微信服务 */
	public static boolean verifyServer(String signature, String timestamp, String nonce) {
		log.info("verify server -> signature={}, timestamp={}, nonce={}, token={}",
				new String[]{signature, timestamp, nonce, TOKEN});

		String[] array = {TOKEN, timestamp, nonce};
		Arrays.sort(array);
		StringBuffer sb = new StringBuffer();
		for(String s : array)
			sb.append(s);

		String hashcode = DigestUtils.sha1Hex(sb.toString());
		if(hashcode.equals(signature))
			return true;

		return false;
	}

}
