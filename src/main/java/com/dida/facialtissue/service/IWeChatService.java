package com.dida.facialtissue.service;


import com.dida.facialtissue.WeChatEntity.WeChatUserInfo;

public interface IWeChatService<T extends WeChatUserInfo>{
	public abstract WeChatUserInfo getUserInfoService(String token, String openId);
	
}
