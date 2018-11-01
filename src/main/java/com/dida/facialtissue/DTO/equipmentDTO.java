package com.dida.facialtissue.DTO;

import java.io.Serializable;

/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/11/1 15:21
 **/
public class equipmentDTO implements Serializable{
    private String device_id;
    private Integer qos;
    private Integer timeout;

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "equipmentDTO{" +
                "device_id='" + device_id + '\'' +
                ", qos=" + qos +
                ", timeout=" + timeout +
                '}';
    }
}
