package com.example.coffeeretail.dto;

import com.example.coffeeretail.protocol.ProtocolUtils;
import lombok.*;

import java.io.IOException;
import java.io.Serializable;


@Getter
@Setter
@Data
public class Response implements Serializable {
    private boolean success;
    private String data;
    private String errorMsg;

    public Response() { }

    public Response(boolean success, String data, String errorMsg) {
        this.success  = success;
        this.data     = data;
        this.errorMsg = errorMsg;
    }

    public static Response okWithData(Object obj) {
        try {
            String json = ProtocolUtils.toJson(obj);
            return new Response(true, json, null);
        } catch (IOException e) {
            return error("Serialization error: " + e.getMessage());
        }
    }

    public static Response okNoData() {
        return new Response(true, null, null);
    }

    public static Response error(String msg) {
        return new Response(false, null, msg);
    }

//    public boolean isSuccess() {
//        return success;
//    }
//    public void setSuccess(boolean success) {
//        this.success = success;
//    }
//
//    public String getData() {
//        return data;
//    }
//    public void setData(String data) {
//        this.data = data;
//    }
//
//    public String getErrorMsg() {
//        return errorMsg;
//    }
//    public void setErrorMsg(String errorMsg) {
//        this.errorMsg = errorMsg;
//    }
}
