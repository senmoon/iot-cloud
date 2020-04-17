package org.iot.iotcommon.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 通用响应对象
 */
@ApiModel(description = "响应对象")
public class BaseResult<T> {
    private static final boolean SUCCESS_STATUS = true;
    private static final String SUCCESS_MESSAGE = "成功";

    @ApiModelProperty(value = "响应码", name = "status", required = true, example = "" + SUCCESS_STATUS)
    private boolean status;

    @ApiModelProperty(value = "响应消息", name = "msg", required = true, example = SUCCESS_MESSAGE)
    private String msg;

    @ApiModelProperty(value = "响应数据", name = "data")
    private T data;

    private BaseResult(boolean status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private BaseResult() {
        this(SUCCESS_STATUS, SUCCESS_MESSAGE);
    }

    private BaseResult(boolean status, String msg) {
        this(status, msg, null);
    }

    private BaseResult(T data) {
        this(SUCCESS_STATUS, SUCCESS_MESSAGE, data);
    }

    public static <T> BaseResult<T> success() {
        return new BaseResult<>();
    }

    public static <T> BaseResult<T> successWithData(T data) {
        return new BaseResult<>(data);
    }

    public static <T> BaseResult<T> successWithStatusAndMsg(boolean status, String msg) {
        return new BaseResult<>(status, msg, null);
    }

    public static <T> BaseResult<T> failWithStatusAndMsg(boolean status, String msg) {
        return new BaseResult<>(status, msg, null);
    }

    public static <T> BaseResult<T> buildWithParam(ResponseParam param) {
        return new BaseResult<>(param.getStatus(), param.getMsg(), null);
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static class ResponseParam {
        private boolean status;
        private String msg;

        private ResponseParam(boolean status, String msg) {
            this.status = status;
            this.msg = msg;
        }

        public static ResponseParam buildParam(boolean status, String msg) {
            return new ResponseParam(status, msg);
        }

        public boolean getStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
