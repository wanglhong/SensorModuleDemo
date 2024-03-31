package cn.wlih.core.base.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.MyBaseService;
import cn.wlih.core.config.ApplicationContextHolder;
import cn.wlih.core.myAnnotate.RelationOneToOne;
import cn.wlih.core.myAnnotate.VariableComment;
import cn.wlih.core.myEnum.DbBaseFieldType;
import cn.wlih.core.base.model.modelDbEnum.IsDeleteEnum;
import cn.wlih.core.myError.BizException;
import cn.wlih.core.sequence.wrapper.IdGeneratorWrapper;
import cn.wlih.core.util.MyClazzUtil;
import cn.wlih.core.util.MyModelUtil;
import cn.wlih.core.util.dbUtil.DbTableUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

@Slf4j
public abstract class MyBaseServiceImpl<M> extends ServiceImpl<MyBaseMapper<M>, M> implements MyBaseService<M> {

    @VariableComment("当前Service关联的主Model实体对象的Class")
    private final Class<M> modelClass;

    public MyBaseServiceImpl() {
        modelClass = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * @return 当前Service的主表Mapper对象
     */
    @VariableComment("获取子类中注入的Mapper类")
    protected abstract MyBaseMapper<M> mapper();

    @Override
    public MyBaseMapper<M> getBaseMapper() {
        return mapper();
    }

    @Override
    public Map<String, String> getModelJson(Class<M> modelClass) {
        Map<String, Map<String, Object>> modelClassFieldsMap = DbTableUtil.collectJavaFields(modelClass);
        Set<String> modelClassFieldsMapKeySet = modelClassFieldsMap.keySet();
        Map<String, String> resultData = new LinkedHashMap<>();
        for (String modelClassFieldsMapKey : modelClassFieldsMapKeySet) {
            Map<String, Object> modelClassField = modelClassFieldsMap.get(modelClassFieldsMapKey);
            StringBuilder fieldComment = new StringBuilder((String) modelClassField.get("fieldComment"));
            Class<?> fieldTypeClass = (Class<?>) modelClassField.get("fieldTypeClass");
            StringBuilder fieldTypeStr = new StringBuilder();
            // 判断 fieldTypeClass 是否属于枚举
            if (fieldTypeClass.isEnum()) {
                List<Object> enumFieldValueByFieldAnnotationList = MyClazzUtil.getEnumFieldValueByFieldAnnotation(fieldTypeClass, JsonValue.class);
                for (int i = 0, listSize = enumFieldValueByFieldAnnotationList.size(); i < listSize; i++) {
                    if (i == 0) {
                        fieldTypeStr.append("['");
                    } else {
                        fieldTypeStr.append(", '");
                    }
                    fieldTypeStr.append(String.valueOf(enumFieldValueByFieldAnnotationList.get(i))).append("'");
                    if (i == listSize - 1) {
                        fieldTypeStr.append("]");
                    }
                }
            } else {
                fieldTypeStr.append(fieldTypeClass.getSimpleName());
            }
            resultData.put(modelClassFieldsMapKey, fieldComment.append(" —— ").append(fieldTypeStr).toString());
        }
        return resultData;
    }

    /**
     * 新增数据
     *
     * @param m 需要新增的数据
     * @return 成功返回true，失败返回false
     */
    @Override
    @Transactional
    public M add(M m) {
        this.buildBaseFieldsValue(m, true);
        if (mapper().insert(m) == 0) {
            MyClazzUtil.setDbBaseFieldValue(m, DbBaseFieldType.ID, null);
        }
        return m;
    }

    /**
     * 根据 ID 选择修改
     *
     * @param entity 实体对象
     */
    @Override
    public boolean updateById(M entity) {
        this.buildBaseFieldsValue(entity, false);
        return mapper().updateById(entity) == 1;
    }

    /**
     * 查询所有数据
     *
     * @return 返回查询结果
     */
    @Override
    @Transactional
    public List<M> selectList(M m) {
        List<M> mList = mapper().selectList(new QueryWrapper<M>(m));
        this.verifyImportForOneToOneRelation(mList);
        return mList;
    }

    @Override
    public Boolean removeByIdList(List<Long> idList) {
        Field dbIdField = MyClazzUtil.getDbBaseField(modelClass, DbBaseFieldType.ID);
        if (dbIdField == null) {
            throw new BizException("Model主键ID为配置！");
        }
        mapper().delete(new QueryWrapper<M>().in(dbIdField.getName(), idList));
        return true;
    }

    /**
     * 验证导入一对一关联数据
     *
     * @param modelDataList
     */
    @Override
    public void verifyImportForOneToOneRelation(List<M> modelDataList) {
        for (M m : modelDataList) {
            Class<?> aClass = m.getClass();
            for (Field field : aClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(RelationOneToOne.class)) {
                    RelationOneToOne relationOneToOne = field.getAnnotation(RelationOneToOne.class);
                    String masterIdField = relationOneToOne.masterIdField();
                    String slaveIdField = relationOneToOne.slaveIdField();
                    Object masterIdFieldValue = MyClazzUtil.getFieldValue(m, masterIdField);
                    if (ObjUtil.isNull(masterIdFieldValue) || ObjUtil.isEmpty(masterIdFieldValue)) {
                        continue;
                    }
                    String slaveServiceName = relationOneToOne.slaveServiceName();
                    MyBaseService slaveService = ApplicationContextHolder.getBean(slaveServiceName);
                    Object one = slaveService.getOne((QueryWrapper) new QueryWrapper().eq(slaveIdField, masterIdField));
                    MyClazzUtil.setFieldValue(m, field, one);
                }
            }
        }
    }

    /**
     * 获取新ID
     * @return 新ID
     */
    protected Long getNewId() {
        return ApplicationContextHolder.getBean(IdGeneratorWrapper.class).nextLongId();
    }

    /**
     * 构建基础字段的值
     * @param m 需要处理的对象
     */
    private void buildBaseFieldsValue(M m, Boolean isAdd) {
        Date nowDate = new Date();
        Long loginUserId = 1001L;
        if (isAdd) {
            // 新增，需要设置 ID、创建时间、创建人ID、逻辑删除
            MyClazzUtil.setDbBaseFieldValue(m, DbBaseFieldType.ID, getNewId());
            MyClazzUtil.setDbBaseFieldValue(m, DbBaseFieldType.CREATE_TIME, nowDate);
            MyClazzUtil.setDbBaseFieldValue(m, DbBaseFieldType.CREATE_USER_ID, loginUserId);
            MyClazzUtil.setDbBaseFieldValue(m, DbBaseFieldType.IS_DELETE, IsDeleteEnum.EXIST);
        }
        // 设置修改时间、修改人ID
        MyClazzUtil.setDbBaseFieldValue(m, DbBaseFieldType.UPDATE_TIME, nowDate);
        MyClazzUtil.setDbBaseFieldValue(m, DbBaseFieldType.UPDATE_USER_ID, loginUserId);
    }

}
