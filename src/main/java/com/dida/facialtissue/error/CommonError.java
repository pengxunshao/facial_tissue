package com.dida.facialtissue.error;

public enum CommonError implements ICommonError {

    REQUEST_PARAMETER_ERROR(10000, "请求参数错误"),
    // 用户错误
    USER_AUTH_ERROR(10001, "用户认证失败"),
    USER_LOGIN_ACCOUNTPWD_ERROR(10002, "用户名或密码错误"),
  
    SYSTEM_ERROR(11000,"系统错误"),
    UPLOAD_FILE_ERROR(12000,"文件上传异常"),
    
    ;

    final private int id;
    final private String msg;

    CommonError(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }
}
