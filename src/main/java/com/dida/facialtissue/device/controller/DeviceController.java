package com.dida.facialtissue.device.controller;

import com.alibaba.fastjson.JSONObject;
import com.dida.facialtissue.commons.ApiResponse;
import com.dida.facialtissue.device.DTO.DeviceDto;
import com.dida.facialtissue.service.IDeviceService;
import com.dida.facialtissue.util.OneNetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    IDeviceService iDeviceService;
    //注册设备
    @PostMapping("/register")
    @ResponseBody
    public ApiResponse registerDevice(@RequestBody DeviceDto deviceDto, @RequestParam(value = "register_code") String register_code) {
        ApiResponse apiResponse = new ApiResponse();
        String register_url = "http://api.heclouds.com/register_de?register_code=" + register_code;
        String param = JSONObject.toJSONString(deviceDto);
        System.out.println(param);
        JSONObject jsonObject = OneNetUtil.sendPost(null, register_url, param);
        if (null!=jsonObject){
            Object data = jsonObject.get("data");
            JSONObject jsonObject1 = JSONObject.parseObject(data.toString());
            System.out.println(data);
            String device_id = jsonObject1.get("device_id").toString();
            String key = jsonObject1.get("key").toString();
            deviceDto.setDevice_id(device_id);
            deviceDto.setKey(key);
            boolean b = iDeviceService.registerDevice(deviceDto);
            if (b){
                apiResponse.setMsg("注册成功!");
                return apiResponse;
            }
        }
        apiResponse.setCode(-1);
        apiResponse.setMsg("注册设备失败!");
        return apiResponse;
    }

    @PostMapping("/outpaper")
    @ResponseBody
    public ApiResponse outpaper(@RequestParam(value = "device_id") String device_id) {
        ApiResponse apiResponse = new ApiResponse();
        String url = "http://api.heclouds.com/cmds?device_id=" + device_id;
        String apikey = null ;
        apikey  = iDeviceService.getApiKeyBydeviceId(device_id);
        //apikey  = "KdGnXFYQ4o2UXPf5qiHPt99ugDc=";
        String param = "{\n" +
                "\t\"FD_HD\":1,\n" +
                "\t\"TG_NUM\":1,\n" +
                "\t\"TG_MES\":\"TXT\"\n" +
                "}";
        JSONObject jsonObject = OneNetUtil.sendPost(apikey, url, param);
        if (0 == (Integer) jsonObject.get("errno")) {
            return apiResponse;
        }
        apiResponse.setCode((Integer) jsonObject.get("errno"));
        apiResponse.setMsg((String) jsonObject.get("error"));
        return apiResponse;
    }


}
