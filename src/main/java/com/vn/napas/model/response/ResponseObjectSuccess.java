package com.vn.napas.model.response;

import lombok.Data;

@Data
public class ResponseObjectSuccess<T> {
    private Integer code;
    private String message;
    private T data;
}
