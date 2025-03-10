package com.ruoyi.common.exception;

public class ParamException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     * <p>
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ParamException() {
    }

    public ParamException(String message) {
        this.message = message;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public ParamException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ParamException setMessage(String message) {
        this.message = message;
        return this;
    }

}
