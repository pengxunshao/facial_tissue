package com.dida.facialtissue.service.impl;

import com.dida.facialtissue.dao.IDeviceDao;
import com.dida.facialtissue.device.DTO.DeviceDto;
import com.dida.facialtissue.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 设备相关服务
 * @author: shaopengxun
 * @createTime:2018/11/2 13:47
 **/
@Service
public class DeviceServiceImpl implements IDeviceService {
    @Autowired
    IDeviceDao iDeviceDao;

    //注册设备
    @Override
    public boolean registerDevice(DeviceDto deviceDto) {
        return iDeviceDao.registerDevice(deviceDto);
    }

    //通过device_id获取apikey
    @Override
    public String getApiKeyBydeviceId(String device_id) {
        return iDeviceDao.getApiKeyBydeviceId(device_id);
    }
}
