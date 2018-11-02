package com.dida.facialtissue.commons;

import java.io.Serializable;

public class ApiResponse implements Serializable{
	private int code = 0;
	private String msg = "ok";
	private Object data;
	
	public ApiResponse() {
	}
	
	public ApiResponse(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public ApiResponse(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
