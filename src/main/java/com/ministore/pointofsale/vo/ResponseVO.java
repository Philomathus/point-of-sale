package com.ministore.pointofsale.vo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ResponseVO<T> {
    private int code;
    private String message;
    private T data;

    public ResponseVO(int code, String message) {
        this(code, message, (T) new ArrayList<>());
    }

    public ResponseVO(int code, String message, T data) {
        setCode(code);
        setMessage(message);
        setData(data);
    }

    public ResponseVO(ServiceStatusCode serviceStatusCode) {
        this(serviceStatusCode, (T) new ArrayList());
    }

    public ResponseVO(ServiceStatusCode serviceStatusCode, T data) {
        this(serviceStatusCode.getCode(), serviceStatusCode.getMessage(), data);
    }
}
