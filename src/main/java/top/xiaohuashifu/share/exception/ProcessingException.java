package top.xiaohuashifu.share.exception;

import top.xiaohuashifu.share.result.ErrorCode;

/**
 * 描述: 处理 处理过程中的异常
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class ProcessingException extends RuntimeException {

    private final ErrorCode errorCode;

    private String message;

    public ProcessingException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ProcessingException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
