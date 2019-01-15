package com.choway.wechat.api.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/message")
public class WechatMessageController {

	@Autowired
	private WechatMessageService wechatMessageService;


	@GetMapping("/verify")
	public String verify(String signature, String timestamp, String nonce, String echostr) {
		return wechatMessageService.verify(signature, timestamp, nonce, echostr);
	}

}
