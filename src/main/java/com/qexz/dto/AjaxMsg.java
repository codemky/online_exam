package com.qexz.dto;

/**
 * ClassName AjaxMsg
 * Description TODO
 * Author LonelySeven
 * Date 2019/5/28 18:15
 * Version 1.0
 **/
public class AjaxMsg {

    private String result_code;
    private String msg;

    @Override
    public String toString() {
        return "AjaxMsg{" +
                "result_code='" + result_code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AjaxMsg(String result_code, String msg) {
        this.result_code = result_code;
        this.msg = msg;
    }
}
