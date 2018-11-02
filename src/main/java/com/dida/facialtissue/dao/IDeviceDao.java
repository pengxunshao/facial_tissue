package com.dida.facialtissue.dao;

import com.dida.facialtissue.device.DTO.DeviceDto;
import com.dida.facialtissue.entity.Device;
import com.dida.facialtissue.entity.DeviceExample;

/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/11/2 13:53
 **/
public interface IDeviceDao extends IDAO<Device, DeviceExample> {
    //注册设备
    boolean registerDevice(DeviceDto deviceDto);
    //通过device_id获取apikey
    String getApiKeyBydeviceId(String device_id);
}
