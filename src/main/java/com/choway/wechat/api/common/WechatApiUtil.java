package com.choway.wechat.api.common;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;


@Slf4j
@Component
public class WechatApiUtil {

	public static String APPID;
	public static String SECRET;
	public static String TOKEN;

	@Value("${wechat.appid}")
	public void setAPPID(String APPID) {
		WechatApiUtil.APPID = APPID;
	}
	@Value("${wechat.secret}")
	public void setSECRET(String SECRET) {
		WechatApiUtil.SECRET = SECRET;
	}
	@Value("${wechat.message.token}")
	public void setTOKEN(String TOKEN) {
		WechatApiUtil.TOKEN = TOKEN;
	}

	public static final String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
	public static final String TEMPLATE_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send";


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


	/** 获取微信接口调用凭证 access_token **/
	public static JSONObject getAccessToken() throws URISyntaxException {
		URI uri = new URIBuilder(GET_ACCESS_TOKEN)
				.setParameter("appid", APPID)
				.setParameter("secret", SECRET)
				.setParameter("grant_type", "client_credential").build();
		JSONObject json = WechatApiHttp.getForJson(uri);

		log.info("get access token -> uri={}", uri);
		log.info("get access token -> resp={}", json);

		return json;
	}


	/** 发送模板消息 **/
	public static JSONObject sendTemplateMessage(String accessToken, String openId, String templateId, String url,
												 String miniproAppid, String miniproPage, JSONObject message) throws URISyntaxException {
		URI uri = new URIBuilder(TEMPLATE_SEND)
				.setParameter("access_token", accessToken)
				.build();

		JSONObject miniprogram = new JSONObject();
		miniprogram.put("appid", miniproAppid);
		miniprogram.put("pagepath", miniproPage);

		JSONObject body = new JSONObject();
		body.put("touser", openId);
		body.put("template_id", templateId);
		body.put("url", url);
		body.put("miniprogram", miniprogram);
		body.put("data", message);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		JSONObject json = WechatApiHttp.postForJson(uri, headers, body);

		log.info("send template message -> uri={}", uri);
		log.info("send template message -> body={}", body);
		log.info("send template message -> resp={}", json);

		return json;
	}

}
