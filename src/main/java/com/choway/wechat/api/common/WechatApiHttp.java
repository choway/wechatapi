package com.choway.wechat.api.common;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;


@Component
public class WechatApiHttp {

	@Autowired
	private RestTemplate wechatRestTemplate;

	private static WechatApiHttp wechatApiHttp;

	@PostConstruct
	public void init() {
		wechatApiHttp = this;
		wechatApiHttp.wechatRestTemplate = this.wechatRestTemplate;
	}

	public static JSONObject getForJson(URI uri) {
		return wechatApiHttp.wechatRestTemplate.getForObject(uri, JSONObject.class);
	}

	public static JSONObject postForJson(URI uri, HttpHeaders headers, JSONObject body) {
		HttpEntity<JSONObject> entity = new HttpEntity<>(body, headers);
		return wechatApiHttp.wechatRestTemplate.postForObject(uri, entity, JSONObject.class);
	}

}
