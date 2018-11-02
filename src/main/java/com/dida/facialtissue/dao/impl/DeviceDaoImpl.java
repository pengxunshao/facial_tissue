package com.dida.facialtissue.dao.impl;

import com.dida.facialtissue.dao.IDeviceDao;
import com.dida.facialtissue.dao.mapper.DeviceMapper;
import com.dida.facialtissue.device.DTO.DeviceDto;
import com.dida.facialtissue.entity.Device;
import com.dida.facialtissue.entity.DeviceExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/11/2 13:56
 **/
@Service
public class DeviceDaoImpl extends DAOImpl<Device, DeviceExample> implements IDeviceDao {
    @Autowired
    DeviceMapper deviceMapper;
    //注册设备
    @Override
    public boolean registerDevice(DeviceDto deviceDto) {
        Device device = new Device();
        device.setDeviceId(deviceDto.getDevice_id());
        device.setApikey(deviceDto.getKey());
        device.setMac(deviceDto.getMac());
        device.setSn(deviceDto.getSn());
        device.setTitle(deviceDto.getTitle());
        deviceMapper.insertSelective(device);
        return true;
    }

    //通过device_id获取apikey
    @Override
    public String getApiKeyBydeviceId(String device_id) {
        DeviceExample deviceExample = new DeviceExample();
        deviceExample.createCriteria().andDeviceIdEqualTo(device_id);
        List<Device> devices = deviceMapper.selectByExample(deviceExample);
        if (!CollectionUtils.isEmpty(devices)){
            return devices.get(0).getApikey();
        }
        return null;
    }
}
