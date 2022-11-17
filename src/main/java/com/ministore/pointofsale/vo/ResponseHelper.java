package com.ministore.pointofsale.vo;

import javax.xml.ws.Response;

public class ResponseHelper {

    public static <T> ResponseVO<T> success() {
        return new ResponseVO<>(ParentCommonStatusCode.SUCCESS);
    }

    public static <T> ResponseVO<T> success(T data) {
        return new ResponseVO<>(ParentCommonStatusCode.SUCCESS, data);
    }

    public static <T> ResponseVO<T> error() {
        return new ResponseVO<>(ParentCommonStatusCode.FAILURE);
    }

    public static <T> ResponseVO<T> invalid() {
        return new ResponseVO<>(ParentCommonStatusCode.INVALID);
    }
}
