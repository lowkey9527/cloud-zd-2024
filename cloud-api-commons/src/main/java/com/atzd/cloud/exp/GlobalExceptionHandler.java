package com.atzd.cloud.exp;


import com.atzd.cloud.resp.ResultData;
import com.atzd.cloud.resp.ReturnCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice//表示是一个处理异常的类
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData exceptionHandler(Exception e){
        System.out.println("#####come in GlobalExceptionHandler");
        log.error("全局异常信息:{}", e.getMessage(),e);

        return ResultData.fail(ReturnCodeEnum.RC500.getCode(), e.getMessage());
    }
}
