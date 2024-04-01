package cn.wlih.core.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class MyModelUtil {

    /**
     * 将Bean转换为Map。
     *
     * @param data Bean数据对象。
     * @param <T>  Bean对象类型。
     * @return 转换后的Map。
     */
    public static <T> Map<String, Object> beanToMap(T data) {
        return BeanUtil.beanToMap(data);
    }

    /**
     * 将Bean的数据列表转换为Map列表。
     *
     * @param dataList Bean数据列表。
     * @param <T>      Bean对象类型。
     * @return 转换后的Map列表。
     */
    public static <T> List<Map<String, Object>> beanToMapList(List<T> dataList) {
        return CollUtil.isEmpty(dataList) ? new LinkedList<>()
                : dataList.stream().map(BeanUtil::beanToMap).collect(Collectors.toList());
    }

    /**
     * 将Map的数据列表转换为Bean列表。
     *
     * @param dataList Map数据列表。
     * @param <T>      Bean对象类型。
     * @return 转换后的Bean对象列表。
     */
    public static <T> List<T> mapToBeanList(List<Map<String, Object>> dataList, Class<T> clazz) {
        return CollUtil.isEmpty(dataList) ? new LinkedList<>()
                : dataList.stream().map(data -> BeanUtil.toBeanIgnoreError(data, clazz)).collect(Collectors.toList());
    }

    /**
     * 拷贝源类型的集合数据到目标类型的集合中，其中源类型和目标类型中的对象字段类型完全相同。
     * NOTE: 该函数主要应用于框架中，Dto和Model之间的copy，特别针对一对一关联的深度copy。
     * 在Dto中，一对一对象可以使用Map来表示，而不需要使用从表对象的Dto。
     *
     * @param sourceCollection 源类型集合。
     * @param targetClazz      目标类型的Class对象。
     * @param <S>              源类型。
     * @param <T>              目标类型。
     * @return copy后的目标类型对象集合。
     */
    public static <S, T> List<T> copyCollectionTo(Collection<S> sourceCollection, Class<T> targetClazz) {
        List<T> targetList = null;
        if (sourceCollection == null) {
            return targetList;
        }
        targetList = new LinkedList<>();
        if (CollUtil.isNotEmpty(sourceCollection)) {
            for (S source : sourceCollection) {
                try {
                    T target = targetClazz.newInstance();
                    BeanUtil.copyProperties(source, target);
                    targetList.add(target);
                } catch (Exception e) {
                    log.error("Failed to call MyModelUtil.copyCollectionTo", e);
                    return Collections.emptyList();
                }
            }
        }
        return targetList;
    }

    /**
     * 拷贝源类型的对象数据到目标类型的对象中，其中源类型和目标类型中的对象字段类型完全相同。
     * NOTE: 该函数主要应用于框架中，Dto和Model之间的copy，特别针对一对一关联的深度copy。
     * 在Dto中，一对一对象可以使用Map来表示，而不需要使用从表对象的Dto。
     *
     * @param source      源类型对象。
     * @param targetClazz 目标类型的Class对象。
     * @param <S>         源类型。
     * @param <T>         目标类型。
     * @return copy后的目标类型对象。
     */
    public static <S, T> T copyTo(S source, Class<T> targetClazz) {
        if (source == null) {
            return null;
        }
        try {
//            T target = targetClazz.newInstance();
//            BeanUtil.copyProperties(source, target);
//            return target;
            return EntityMapperUtil.convert(source, targetClazz);
        } catch (Exception e) {
            log.error("Failed to call MyModelUtil.copyTo", e);
            return null;
        }
    }

    /**
     * 映射Model主对象的Class名称，到Model所对应的表名称。
     *
     * @param modelClazz Model主对象的Class。
     * @return Model对象对应的数据表名称。
     */
    public static String mapToTableName(Class<?> modelClazz) {
        TableName t = modelClazz.getAnnotation(TableName.class);
        return t == null ? null : t.value();
    }

    /**
     * 根据主对象和关联对象各自的关联Id函数，将主对象列表和关联对象列表中的数据关联到一起，并将关联对象
     * 设置到主对象的指定关联字段中。
     * NOTE: 用于主对象关联字段中，没有包含RelationOneToOne注解的场景。
     *
     * @param thisClazz         主对象的Class对象。
     * @param thisModelList     主对象列表。
     * @param thisIdGetterFunc  主对象Id的Getter函数。
     * @param thatModelList     关联对象列表。
     * @param thatIdGetterFunc  关联对象Id的Getter函数。
     * @param thisRelationField 主对象中保存被关联对象的字段名称。
     * @param <T>               主表对象类型。
     * @param <R>               从表对象类型。
     */
    public static <T, R> void makeOneToOneRelation(
            Class<T> thisClazz,
            List<T> thisModelList,
            Function<T, Object> thisIdGetterFunc,
            List<R> thatModelList,
            Function<R, Object> thatIdGetterFunc,
            String thisRelationField) {
        makeOneToOneRelation(thisClazz, thisModelList,
                thisIdGetterFunc, thatModelList, thatIdGetterFunc, thisRelationField, false);
    }

    /**
     * 根据主对象和关联对象各自的关联Id函数，将主对象列表和关联对象列表中的数据关联到一起，并将关联对象
     * 设置到主对象的指定关联字段中。
     * NOTE: 用于主对象关联字段中，没有包含RelationOneToOne注解的场景。
     *
     * @param thisClazz         主对象的Class对象。
     * @param thisModelList     主对象列表。
     * @param thisIdGetterFunc  主对象Id的Getter函数。
     * @param thatModelList     关联对象列表。
     * @param thatIdGetterFunc  关联对象Id的Getter函数。
     * @param thisRelationField 主对象中保存被关联对象的字段名称。
     * @param orderByThatList   如果为true，则按照ThatModelList的顺序输出。同时thisModelList被排序后的新列表替换。
     * @param <T>               主表对象类型。
     * @param <R>               从表对象类型。
     */
    public static <T, R> void makeOneToOneRelation(
            Class<T> thisClazz,
            List<T> thisModelList,
            Function<T, Object> thisIdGetterFunc,
            List<R> thatModelList,
            Function<R, Object> thatIdGetterFunc,
            String thisRelationField,
            boolean orderByThatList) {
        if (CollUtil.isEmpty(thisModelList)) {
            return;
        }
        Field thisTargetField = ReflectUtil.getField(thisClazz, thisRelationField);
        boolean isMap = thisTargetField.getType().equals(Map.class);
        if (orderByThatList) {
            List<T> newThisModelList = new LinkedList<>();
            Map<Object, ? extends T> thisModelMap =
                    thisModelList.stream().collect(Collectors.toMap(thisIdGetterFunc, c -> c));
            thatModelList.forEach(thatModel -> {
                Object thatId = thatIdGetterFunc.apply(thatModel);
                if (thatId != null) {
                    T thisModel = thisModelMap.get(thatId);
                    if (thisModel != null) {
                        ReflectUtil.setFieldValue(thisModel, thisTargetField, normalize(isMap, thatModel));
                        newThisModelList.add(thisModel);
                    }
                }
            });
            thisModelList.clear();
            thisModelList.addAll(newThisModelList);
            return;
        }
        Map<Object, R> thatMadelMap =
                thatModelList.stream().collect(Collectors.toMap(thatIdGetterFunc, c -> c));
        thisModelList.forEach(thisModel -> {
            Object thisId = thisIdGetterFunc.apply(thisModel);
            if (thisId != null) {
                R thatModel = thatMadelMap.get(thisId);
                if (thatModel != null) {
                    ReflectUtil.setFieldValue(thisModel, thisTargetField, normalize(isMap, thatModel));
                }
            }
        });
    }

    private static <M> Object normalize(boolean isMap, M model) {
        return isMap ? BeanUtil.beanToMap(model) : model;
    }

    /**
     * 为实体对象字段设置缺省值。如果data对象中指定字段的值为NULL，则设置缺省值，否则跳过。
     *
     * @param data         实体对象。
     * @param fieldName    实体对象字段名。
     * @param defaultValue 缺省值。
     * @param <M>          实体对象类型。
     * @param <V>          缺省值类型。
     */
    public static <M, V> void setDefaultValue(M data, String fieldName, V defaultValue) {
        Object v = ReflectUtil.getFieldValue(data, fieldName);
        if (v == null) {
            ReflectUtil.setFieldValue(data, fieldName, defaultValue);
        }
    }

    /**
     * 根据数据对象指定字段的类型，将参数中的字段值集合转换为匹配的值类型集合。
     * @param clazz       数据对象的Class。
     * @param fieldName   字段名。
     * @param fieldValues 字符型的字段值集合。
     * @param <M> 对象类型。
     * @return 转换后的字段值集合。
     */
    public static <M> Set<? extends Serializable> convertToTypeValues(
            Class<M> clazz, String fieldName, List<String> fieldValues) {
        Field f = ReflectUtil.getField(clazz, fieldName);
        if (f == null) {
            String errorMsg = "数据对象 [" + clazz.getSimpleName() + " ] 中，不存在该数据字段 [" + fieldName + "]!";
            throw new RuntimeException(errorMsg);
        }
        if (f.getType().equals(Long.class)) {
            return fieldValues.stream().map(Long::valueOf).collect(Collectors.toSet());
        } else if (f.getType().equals(Integer.class)) {
            return fieldValues.stream().map(Integer::valueOf).collect(Collectors.toSet());
        }
        return new HashSet<>(fieldValues);
    }

    /**
     * 私有构造函数，明确标识该常量类的作用。
     */
    private MyModelUtil() {
    }

}
