package com.alivin.myblog.exception;

import com.alivin.myblog.utils.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * 统一异常类
 *
 * @author Fer
 * @date 2021/8/14
 */
public class BusinessException extends RuntimeException {

    private final static Logger LOGGER = LoggerFactory.getLogger(BusinessException.class);
    protected String errorCode;
    protected String[] errorMessageArguments;
    public CommonResult result;

    protected BusinessException() {
        this("");
    }

    public BusinessException(String message) {
        super(message);
        this.errorCode = "fail";
        this.errorMessageArguments = new String[0];
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "fail";
        this.errorMessageArguments = new String[0];
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String[] getErrorMessageArguments() {
        return this.errorMessageArguments;
    }

    public void setErrorMessageArguments(String[] errorMessageArguments) {
        this.errorMessageArguments = errorMessageArguments;
    }

    public static BusinessException withErrorCode(String errorCode) {
        BusinessException businessException = new BusinessException();
        businessException.errorCode = errorCode;
        return businessException;
    }

    public static BusinessException fromCommonResult(CommonResult commonResult) {
        BusinessException businessException = new BusinessException();
        if(commonResult == null) {
            commonResult = CommonResult.failed("NULL");
        }
        businessException.result = commonResult;
        return businessException;
    }

    public BusinessException withErrorMessageArguments(String... errorMessageArguments) {
        if(errorMessageArguments != null) {
            this.errorMessageArguments = errorMessageArguments;
        }

        return this;
    }

    public CommonResult getResult() {
        if (this.result != null) {
            return this.result;
        } else {
            this.result = CommonResult.failed(this.errorCode);
            if(this.getErrorMessageArguments() != null && this.getErrorMessageArguments().length > 0) {
                try {
                    this.result.setMessage(MessageFormat.format(this.result.getMessage(), (Object) this.getErrorMessageArguments()));
                } catch (Exception var2) {
                    LOGGER.error(var2.getMessage());
                }
            }
            return this.result;
        }

    }

}
