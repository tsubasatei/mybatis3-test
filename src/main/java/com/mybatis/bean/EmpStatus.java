package com.mybatis.bean;


public enum EmpStatus {
    LOGIN(100, "用户登录"), LOGOUT(101, "用户登出"), REMOVE(102, "用户不存在");

    private Integer code;

    private String msg;

    EmpStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    // 按照状态码返回枚举对象
    public static EmpStatus getEmpStatusByCode(Integer code){
        switch (code) {
            case 100:
                return LOGIN;
            case 101:
                return LOGOUT;
            case 102:
                return REMOVE;
            default:
                return LOGOUT;
        }
    }
}
