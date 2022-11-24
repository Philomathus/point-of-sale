package com.ministore.pointofsale.exception;

import com.ministore.pointofsale.vo.ResponseVO;
import com.ministore.pointofsale.vo.ServiceStatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseVO<ServiceStatusCode> handleServiceException(ServiceException ex) {
        return new ResponseVO<>(new ServiceStatusCode(ex.getCode(), ex.getMessage()));
    }

}
