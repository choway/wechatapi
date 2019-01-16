package com.choway.wechat.api.common;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

import java.net.URISyntaxException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatApiUtilTest {

	@Test
	public void getAccessToken() {
		try {
			JSONObject json = WechatApiUtil.getAccessToken();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void sendTemplateMessage() {
		try {
			JSONObject json = WechatApiUtil.getAccessToken();
			String accessToken = json.getString("access_token");

			String openId = "o5x920oziEKozo5HPv9yXgqkPGO0";
			String templateId = "NbQ-qg4e1-2gcvWjeQQw3xM8jDHCQjfCRSTt3CPSSXM";
			String url = "https://choway.gitee.io/";
			String miniproAppid = "";
			String miniproPage = "";

			JSONObject first = new JSONObject();
			first.put("value", "header");
			JSONObject keyword1 = new JSONObject();
			keyword1.put("value", "123");
			JSONObject keyword2 = new JSONObject();
			keyword2.put("value", "456");
			JSONObject keyword3 = new JSONObject();
			keyword3.put("value", "789");
			JSONObject remark = new JSONObject();
			remark.put("value", "remark");

			JSONObject message = new JSONObject();
			message.put("first", first);
			message.put("keyword1", keyword1);
			message.put("keyword2", keyword2);
			message.put("keyword3", keyword3);
			message.put("remark", remark);

			WechatApiUtil.sendTemplateMessage(accessToken, openId, templateId, url, miniproAppid, miniproPage, message);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
	}

}
