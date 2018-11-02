package com.dida.facialtissue.service;

import com.dida.facialtissue.device.DTO.DeviceDto;

/**
 * @description: 设备相关服务
 * @author: shaopengxun
 * @createTime:2018/11/2 13:46
 **/
public interface IDeviceService {
    //注册设备
    boolean registerDevice(DeviceDto deviceDto);

    //通过device_id获取apikey
    String getApiKeyBydeviceId(String device_id);
}
