package com.dida.facialtissue.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 关注,订阅
 * @author: shaopengxun
 * @createTime:2018/11/1 11:51
 **/
public interface ISubscribeService {
    public String Subscribe(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
