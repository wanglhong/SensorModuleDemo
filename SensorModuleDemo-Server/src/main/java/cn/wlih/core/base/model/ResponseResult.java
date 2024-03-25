package cn.wlih.core.base.model;

import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import cn.wlih.core.myError.ExceptionEnum;
import lombok.Data;

@Data
@ClassComment("统一响应对象")
public class ResponseResult<T> {

    /**
     * 为了优化性能，所有没有携带数据的正确结果，均可用该对象表示。
     */
    private static final ResponseResult<Void> OK = new ResponseResult<>();

    @VariableComment("请求成功与否")
    private Boolean success = true;
    @VariableComment("响应代码")
    private Integer code = 200;
    @VariableComment("响应信息")
    private String msg = "success";
    @VariableComment("响应数据")
    private T data;

    /**
     * 成功响应
     * @return 响应对象
     */
    public static ResponseResult<Void> success() {
        return OK;
    }

    /**
     * 成功响应
     * @param data 响应数据
     * @return 响应对象
     * @param <T>
     */
    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> resp = new ResponseResult<>();
        resp.data = data;
        return resp;
    }

    /**
     * 失败响应
     * @return 响应对象
     */
    public static <T> ResponseResult<T> error() {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.success = false;
        responseResult.code = 500;
        responseResult.msg = "NO-MESSAGE";
        return responseResult;
    }

    /**
     * 失败响应
     * @param msg 响应信息
     * @return 响应对象
     * @param <T>
     */
    public static <T> ResponseResult<T> error(String msg) {
        ResponseResult<T> resp = new ResponseResult<>();
        resp.success = false;
        resp.code = 500;
        resp.msg = msg;
        return resp;
    }
    public static <T> ResponseResult<T> error(Integer code, String msg) {
        ResponseResult<T> resp = new ResponseResult<>();
        resp.success = false;
        resp.code = code;
        resp.msg = msg;
        return resp;
    }
    public static <T> ResponseResult<T> error(ExceptionEnum exceptionEnum) {
        ResponseResult<T> resp = new ResponseResult<>();
        resp.success = false;
        resp.code = exceptionEnum.getResultCode();
        resp.msg = exceptionEnum.getResultMsg();
        return resp;
    }

}
