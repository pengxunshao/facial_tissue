package com.dida.facialtissue.device.DTO;

import java.io.Serializable;

/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/11/1 18:20
 **/
public class DeviceDto implements Serializable {
    private String sn;
    private String mac;
    private String title;
    private String device_id;
    private String key;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "DeviceDto{" +
                "sn='" + sn + '\'' +
                ", mac='" + mac + '\'' +
                ", title='" + title + '\'' +
                ", device_id='" + device_id + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
