package cn.wlih.core.base.controller;

import cn.hutool.core.util.ReflectUtil;
import cn.wlih.core.base.model.ResponseResult;
import cn.wlih.core.base.service.MyBaseService;
import cn.wlih.core.config.ApplicationContextHolder;
import cn.wlih.core.util.NameFormatConversionUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class MyBaseController<M> {

    /**
     * 所属的模型实体类
     */
    private Class<M> modelClass;
    /**
     * 主键ID字段名称
     */
    private String idFieldName;

    /**
     * BaseService 实例
     */
    private MyBaseService<M> baseService;

    public MyBaseController() {
        this.modelClass = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Field[] fields = ReflectUtil.getFields(modelClass);
        for (Field field : fields) {
            if (this.idFieldName == null && null != field.getAnnotation(TableId.class)) {
                this.idFieldName = field.getName();
                break;
            }
        }
    }

    private MyBaseService<M> getBaseService() {
        if (this.baseService == null) {
            this.baseService =
                    ApplicationContextHolder.getBean(NameFormatConversionUtil.toLowerCaseFirstOne(modelClass.getSimpleName() + "Service"));
        }
        return this.baseService;
    }

    /**
     * 获取基础数据对象JSON数据
     */
    @GetMapping("/getModelJson")
    @ResponseBody
    public ResponseResult<Map<String, String>> getModelJson() {
        log.debug("BaseController 获取JSON数据");
        Map<String, String> resultData = getBaseService().getModelJson(modelClass);
        return ResponseResult.success(resultData);
    }

    /**
     * 基础新增接口
     * @param m 实体
     */
    @PostMapping("/add")
    public ResponseResult<M> add(@RequestBody M m) {
        // TODO 统一异常处理
        return ResponseResult.success(getBaseService().add(m));
    }

    /**
     * 基础修改接口
     */
    @PostMapping("/updateById")
    public ResponseResult<Void> updateById(@RequestBody M m) {
        getBaseService().updateById(m);
        return ResponseResult.success();
    }

    /**
     * 基础删除接口
     */
    @PostMapping("/delete")
    public ResponseResult<Void> delete(@RequestBody M m) {
        getBaseService().removeById(m);
        return ResponseResult.success();
    }

    /**
     * 基础查询接口
     */
    @PostMapping("/list")
    public ResponseResult<List<M>> selectList(@RequestBody M m) {
        List<M> list = getBaseService().selectList(m);
        return ResponseResult.success(list);
    }

}
