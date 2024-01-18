package com.vn.napas.utils;

import com.vn.napas.model.response.BaseResponse;
import com.vn.napas.model.response.ResponseData;

public class ResponseUtils {
    public static BaseResponse setResponseError(String param) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(400);
        baseResponse.setMessage("Invalid input data: " + param);
        baseResponse.setDescription(Constants.DATA + param + Constants.INVALID);
        baseResponse.setData(new ResponseData());
        return baseResponse;
    }

    public static BaseResponse responseFail(String err) {
        BaseResponse responseObject = new BaseResponse();
        responseObject.setCode(400);
        responseObject.setMessage(err);
        responseObject.setData(null);
        return responseObject;
    }
}
