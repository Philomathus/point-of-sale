package com.ministore.pointofsale.vo;

import lombok.Data;
import lombok.ToString;

import java.util.Objects;

@Data
@ToString
public class ServiceStatusCode {
    private int code;
    private String message = "";
    private String type = "";

    public ServiceStatusCode(int code) {
        this.code = code;
    }

    public ServiceStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServiceStatusCode(int code, String message, String type) {
        this.code = code;
        this.message = message;
        this.type = type;
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(!(o instanceof ServiceStatusCode)) {
            return false;
        } else {
            ServiceStatusCode that = (ServiceStatusCode) o;
            return this.code == that.code && Objects.equals(this.type, that.type);
        }
    }

    public int hashCode() {
        return Objects.hash(code, type);
    }
}
