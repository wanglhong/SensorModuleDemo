package cn.wlih.core.base.model;

import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import lombok.Data;

@Data
@ClassComment("统一响应对象")
public class ResponseResult<T> {

    @VariableComment("请求成功与否")
    private boolean success = true;
    @VariableComment("响应信息")
    private String msg = "SUCCESS";
    @VariableComment("响应数据")
    private T data;

    /**
     * 成功响应
     * @return 响应对象
     */
    public static ResponseResult success() {
        return new ResponseResult<>();
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
        resp.msg = msg;
        return resp;
    }

}
