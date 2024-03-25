package cn.wlih.core.myError;

import cn.hutool.core.util.StrUtil;
import cn.wlih.core.base.model.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     * @param req request请求
     * @param e 业务异常
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public ResponseResult<Void> bizExceptionHandler(HttpServletRequest req, BizException e){
        logger.error("业务处理异常！ErrorMsg：{}。", e.getErrorMsg());
        return ResponseResult.error(e.getErrorCode(), e.getErrorMsg());
    }

    /**
     * 处理空指针的异常
     * @param req request请求
     * @param e 业务异常
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public ResponseResult<Void> exceptionHandler(HttpServletRequest req, NullPointerException e){
        String message = e.getMessage();
        logger.error("发生空指针异常！ErrorMsg：{}。", message, e);
        if (StrUtil.isBlank(message)) {
            return ResponseResult.error(ExceptionEnum.BODY_NOT_MATCH);
        }
        return ResponseResult.error(400, message);
    }

    /**
     * 处理其他异常
     * @param req request请求
     * @param e 业务异常
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseResult<Void> exceptionHandler(HttpServletRequest req, Exception e){
        String message = e.getMessage();
        logger.error("未知异常！ErrorMsg：{}。", message, e);
        if (StrUtil.isBlank(message)) {
            return ResponseResult.error(ExceptionEnum.INTERNAL_SERVER_ERROR);
        }
        return ResponseResult.error(500, message);
    }

}
