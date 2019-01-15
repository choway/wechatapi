package com.choway.wechat.api.message;

import com.choway.wechat.api.common.WechatApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Slf4j
@Service
public class WechatMessageServiceImpl implements WechatMessageService {

	@Override
	public String verify(String signature, String timestamp, String nonce, String echostr) {
		log.info("wechat message verify -> signature={}, timestamp={}, nonce={}, echostr={}",
				new String[]{signature, timestamp, nonce, echostr});

		if (StringUtils.isEmpty(signature) ||
			StringUtils.isEmpty(timestamp) ||
			StringUtils.isEmpty(nonce) ||
			StringUtils.isEmpty(echostr)
		) return null;

		if (WechatApiUtil.verifyServer(signature, timestamp, nonce))
			return echostr;

		return null;
	}

}
