package cn.wlih.core.base.model;

import cn.wlih.core.myError.ExceptionEnum;
import cn.wlih.core.util.ContextUtil;
import com.alibaba.fastjson.JSON;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Data
@Schema(title = "统一响应对象")
public class ResponseResult<T> {

    /**
     * 为了优化性能，所有没有携带数据的正确结果，均可用该对象表示。
     */
    private static final ResponseResult<Void> OK = new ResponseResult<>();

    @Schema(title = "请求结果（true or false）")
    private Boolean success = true;

    @Schema(title = "响应代码")
    private Integer code = 200;

    @Schema(title = "响应信息")
    private String msg = "success";

    @Schema(title = "响应数据")
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

    public static void output(int httpStatus) throws IOException {
        output(httpStatus, null);
    }

    public static <T> void output(ResponseResult<T> responseResult) throws IOException {
        output(HttpServletResponse.SC_OK, responseResult);
    }

    /**
     * 通过HttpServletResponse直接输出应该信息的工具方法。
     *
     * @param httpStatus     http状态码。
     * @param responseResult 应答内容。
     * @param <T>            数据对象类型。
     * @throws IOException 异常错误。
     */
    public static <T> void output(int httpStatus, ResponseResult<T> responseResult) throws IOException {
        if (httpStatus != HttpServletResponse.SC_OK) {
            log.error(JSON.toJSONString(responseResult));
        } else {
            log.info(JSON.toJSONString(responseResult));
        }
        HttpServletResponse response = ContextUtil.getHttpResponse();
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        response.setStatus(httpStatus);
        if (responseResult != null) {
            out.print(JSON.toJSONString(responseResult));
        }
        out.flush();
    }

}
