package cn.wlih.core.base.controller;

import cn.hutool.core.util.ReflectUtil;
import cn.wlih.core.base.model.Page;
import cn.wlih.core.base.model.ResponseResult;
import cn.wlih.core.base.service.MyBaseService;
import cn.wlih.core.config.ApplicationContextHolder;
import cn.wlih.core.myAnnotate.MyRequestBody;
import cn.wlih.core.myEnum.DbBaseFieldType;
import cn.wlih.core.myError.BizException;
import cn.wlih.core.util.MyClazzUtil;
import cn.wlih.core.util.MyModelUtil;
import cn.wlih.core.util.NameFormatConversionUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class MyBaseController<M, MDTO, MVO> {

    /**
     * 所属的模型实体类
     */
    private Class<M> modelClass;
    private Class<MDTO> modelDtoClass;
    private Class<MVO> modelVoClass;
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
        this.modelDtoClass = (Class<MDTO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        this.modelVoClass = (Class<MVO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
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

//    /**
//     * 获取基础数据对象JSON数据
//     */
//    @Deprecated
//    @Operation(summary = "获取对象的JSON描述接口", description = "方便用于传参")
//    @GetMapping("/getModelJson")
//    @ResponseBody
//    public ResponseResult<Map<String, String>> getModelJson() {
//        Map<String, String> resultData = getBaseService().getModelJson(modelClass);
//        return ResponseResult.success(resultData);
//    }

    /**
     * 获取基础数据对象JSON数据
     */
    @Deprecated
    @ApiOperationSupport(order = 100)
    @Operation(summary = "获取对象的JSON描述接口", description = "方便用于传参")
    @GetMapping("/getModelJson")
    @ResponseBody
    public ResponseResult<MVO> getModelJson() throws InstantiationException, IllegalAccessException {
        MVO mvo = modelVoClass.newInstance();
        return ResponseResult.success(mvo);
    }

    /**
     * 基础新增接口
     * @param modelDto 实体
     */
    @ApiOperationSupport(order = 1)
    @Operation(summary = "基础 - 新增接口")
    @PostMapping("/add")
    public ResponseResult<MVO> add(@Parameter(description = "新增的对象信息") @MyRequestBody MDTO modelDto) {
        M m = MyModelUtil.copyTo(modelDto, modelClass);
        M mResult = getBaseService().add(m);
        MVO mVo = MyModelUtil.copyTo(mResult, modelVoClass);
        return ResponseResult.success(mVo);
    }

    /**
     * 基础查询接口
     */
    @ApiOperationSupport(order = 2)
    @Operation(summary = "基础 - 查询接口")
    @PostMapping("/list")
    public ResponseResult<Page<M, MVO>> selectList(
            @MyRequestBody(required = false) MDTO modelDto, @MyRequestBody Page<M, MVO> page) {
        M m = MyModelUtil.copyTo(modelDto, modelClass);
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<M> list = getBaseService().selectList(m);
        Page.setPageInfo(page, list, modelVoClass);
        return ResponseResult.success(page);
    }

    /**
     * 基础修改接口
     */
    @ApiOperationSupport(order = 3)
    @Operation(summary = "基础 - 修改接口（通过ID）")
    @PostMapping("/updateById")
    public ResponseResult<Void> updateById(@Parameter(description = "修改的对象信息") @MyRequestBody MDTO modelDto) {
        M m = MyModelUtil.copyTo(modelDto, modelClass);
        Field dbIdField = MyClazzUtil.getDbBaseField(modelClass, DbBaseFieldType.ID);
        if (dbIdField == null) {
            return ResponseResult.error("Id不能为空！");
        }
        // 提高访问权限
        try {
            dbIdField.setAccessible(true);
            Long id = (Long) dbIdField.get(m);
            if (null == id) {
                return ResponseResult.error("Id不能为空！");
            }
            M mOriginal = getBaseService().getById(id);
            if (mOriginal == null) {
                return ResponseResult.error("数据不存在，无法修改！");
            }
        } catch (IllegalAccessException e) {
            return ResponseResult.error("Id不能为空！");
        }
        getBaseService().updateById(m);
        return ResponseResult.success();
    }

    /**
     * 基础删除接口
     */
    @ApiOperationSupport(order = 4)
    @Operation(summary = "基础 - 删除接口（通过ID）")
    @PostMapping("/delete")
    public ResponseResult<Void> delete(@Parameter(description = "删除的对象ID") @MyRequestBody Long id) {
        if (id == null) {
            return ResponseResult.error("Id不能为空！");
        }
        M m = getBaseService().getById(id);
        if (m == null) {
            return ResponseResult.error("数据不存在，无法删除！");
        }
        if (getBaseService().removeById(id)) {
            return ResponseResult.success();
        }
        return ResponseResult.error("删除失败，请重试！");
    }

    /**
     * 基础删除接口
     */
    @ApiOperationSupport(order = 5)
    @Operation(summary = "基础 - 删除接口（通过ID集合）")
    @PostMapping("/removeByIdList")
    public ResponseResult<Void> removeByIdList(@Parameter(description = "删除的对象ID集合") @MyRequestBody List<Long> idList) {
        if (idList == null || idList.isEmpty()) {
            return ResponseResult.error("idList不能为空！");
        }
        if (getBaseService().removeByIdList(idList)) {
            return ResponseResult.success();
        }
        return ResponseResult.error("删除失败，请重试！");
    }

}
