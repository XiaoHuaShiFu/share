package top.xiaohuashifu.share.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.xiaohuashifu.share.exception.ProcessingException;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.ErrorResponse;

/**
 * 描述: 对操作异常进行统一处理
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@ControllerAdvice
@ResponseBody
public class ProcessingExceptionHandler {

    /**
     * 用于处理处理过程中的异常ProcessingException
     * @param e ProcessingException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(ProcessingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> processingExceptionHandler(ProcessingException e) {
        ErrorCode errorCode = e.getErrorCode();
        String message = e.getMessage();
        ErrorResponse errorResponse;
        if (message == null) {
            errorResponse = new ErrorResponse(errorCode.getError(), errorCode.getMessage());
        } else {
            errorResponse = new ErrorResponse(errorCode.getError(), message);
        }
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

}