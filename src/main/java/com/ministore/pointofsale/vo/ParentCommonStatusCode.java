package com.ministore.pointofsale.vo;

public class ParentCommonStatusCode {

    public static final ServiceStatusCode SUCCESS =  new ServiceStatusCode(200, "Successful Operation");
    public static final ServiceStatusCode FAILURE =  new ServiceStatusCode(500, "An exception occurred in the server side!");
    public static final ServiceStatusCode INVALID = new ServiceStatusCode(406, "The input data is invalid!");

}
