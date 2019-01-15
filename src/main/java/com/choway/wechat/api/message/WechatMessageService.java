package com.choway.wechat.api.message;

public interface WechatMessageService {

	String verify(String signature, String timestamp, String nonce, String echostr);

}
