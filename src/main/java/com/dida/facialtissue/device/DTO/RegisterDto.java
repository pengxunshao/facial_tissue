package com.dida.facialtissue.device.DTO;/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/11/2 13:15
 **/

import java.io.Serializable;

/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/11/2 13:15
 **/
public class RegisterDto implements Serializable {
    private String device_id;
    private String key;

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
        return "RegisterDto{" +
                "device_id='" + device_id + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
