package com.mousse.dto;

import com.mousse.enums.CustomizeErrorCode;
import com.mousse.exception.CustomizeException;
import lombok.Data;

/**
 * @author mousse
 * @data 2021/8/31
 */
@Data
public class Result {

    private int code;
    private String message;
    private Object data;

    public static Result ok(Object data){
        Result result = new Result();
        result.setData(data);
        result.setCode(200);
        result.setMessage("操作成功");
        return result;
    }

    public static Result fail(int code,String message){
        Result result = new Result();
        result.setMessage(message);
        result.setCode(code);
        return result;
    }

    public static Result fail(CustomizeException exception){
        return fail(exception.getCode(),exception.getMessage());
    }

    public static Result fail(CustomizeErrorCode customizeErrorCode){
        Result result = new Result();
        result.setMessage(customizeErrorCode.getMessage());
        result.setCode(customizeErrorCode.getCode());
        return result;
    }

}
