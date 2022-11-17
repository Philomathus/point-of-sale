package com.ministore.pointofsale.exception;

import com.ministore.pointofsale.vo.ResponseVO;
import com.ministore.pointofsale.vo.ServiceStatusCode;
import lombok.Data;

@Data
public class ServiceException extends RuntimeException {
    private int code;
    private String message;
    private Object data;
    private String type;

    public ServiceException(ServiceStatusCode httpStatus) {
        this.code = httpStatus.getCode();
        this.message = httpStatus.getMessage();
    }

    public ServiceException(ServiceStatusCode httpStatus, String type) {
        this.code = httpStatus.getCode();
        this.message = httpStatus.getMessage();
        this.type = type;
    }

    public <T> ServiceException(ServiceStatusCode httpStatus, T data) {
        this.code = httpStatus.getCode();
        this.message = httpStatus.getMessage();
        this.data = data;
    }

    public <T> ServiceException(ServiceStatusCode httpStatus, T data, String type) {
        this.code = httpStatus.getCode();
        this.message = httpStatus.getMessage();
        this.data = data;
        this.type = type;
    }

    public ServiceException(int status, String message) {
        this.code = status;
        this.message = message;
    }

    public <T> ServiceException(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public <T> ServiceException(int code, String message, T data, String type) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.type = type;
    }

    public ResponseVO<Object> toResponseVO() {
        return new ResponseVO(this.code, this.message, this.data);
    }

    public ResponseVO<Object> toResponseVO(ServiceException se) {
        return new ResponseVO(se.code, se.getMessage(), se.data);
    }
}