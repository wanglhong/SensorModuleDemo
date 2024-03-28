//package cn.wlih.core.openApi;
//
//import cn.hutool.core.collection.CollUtil;
//import cn.hutool.core.map.MapUtil;
//import cn.hutool.core.util.ArrayUtil;
//import cn.hutool.core.util.StrUtil;
//import cn.wlih.core.myAnnotate.MyRequestBody;
//import io.swagger.v3.oas.annotations.Parameters;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.models.Operation;
//import lombok.extern.slf4j.Slf4j;
//import org.springdoc.core.customizers.GlobalOperationCustomizer;
//import org.springframework.stereotype.Component;
//import org.springframework.web.method.HandlerMethod;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.*;
//import java.util.*;
//import java.util.stream.Stream;
//
//@Slf4j
//@Component
//public class MyGlobalOperationCustomer implements GlobalOperationCustomizer {
//
//    // 引用键名
//    private static final String REF_KEY = "$ref";
//    // 引用模式前缀
//    private static final String REF_SCHEMA_PREFIX = "#/components/schemas/";
//    // 类属性缓存映射，用于缓存类的属性集合，避免多次反射获取。
//    private final Map<Class<?>, Set<String>> cacheClassProperties = MapUtil.newHashMap();
//    // Orange 表单扩展名称
//    private static final String EXTENSION_ORANGE_FORM_NAME = "x-orangeforms";
//    // 忽略参数的 Orange 表单扩展名称
//    private static final String EXTENSION_ORANGE_FORM_IGNORE_NAME = "x-orangeforms-ignore-parameters";
//
//    /**
//     * 自定义全局操作定制器
//     * @param operation input operation
//     * @param handlerMethod original handler method
//     * @return
//     */
//    @Override
//    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
//        // 处理操作摘要
//        this.handleSummary(operation, handlerMethod);
//        if (handlerMethod.getMethod().getParameterCount() <= 0) { // 如果没有参数
//            return operation;
//        }
//        Parameter[] parameters = handlerMethod.getMethod().getParameters(); // 获取方法参数
//        if (ArrayUtil.isEmpty(parameters)) { // 参数为空
//            return operation;
//        }
//        // 参数属性
//        Map<String, Object> properties = MapUtil.newHashMap();
//        // 扩展属性
//        Map<String, Object> extensions = MapUtil.newHashMap();
//        // 忽略的字段名
//        Set<String> ignoreFieldName = CollUtil.newHashSet();
//        // 必需的参数名列表
//        List<String> required = new ArrayList<>();
//        // 获取参数描述
//        Map<String, io.swagger.v3.oas.annotations.Parameter> paramMap = getParameterDescription(handlerMethod.getMethod());
//        for (Parameter parameter : parameters) { // 遍历参数
//            Annotation[] annos = parameter.getAnnotations(); // 获取参数的注解
//            if (ArrayUtil.isEmpty(annos)) { // 没有注解
//                continue;
//            }
//            // 统计 MyRequestBody 注解的数量
//            long count = Stream.of(annos).filter(anno -> anno.annotationType().equals(MyRequestBody.class)).count();
//            if (count > 0) { // 存在 MyRequestBody 注解
//                // 处理参数详情
//                this.handleParameterDetail(parameter, properties, paramMap, ignoreFieldName, required);
//            }
//        }
//        if (!properties.isEmpty()) { // 参数属性不为空
//            extensions.put("properties", properties); // 设置属性
//            extensions.put("type", "object"); // 设置类型为对象
//            // 设置 required 字段
//            if (!required.isEmpty()) {
//                extensions.put("required", required);
//            }
//            String generateSchemaName = handlerMethod.getMethod().getName() + "DynamicReq"; // 生成动态请求的 schema 名称
//            Map<String, Object> orangeExtensions = MapUtil.newHashMap();
//            orangeExtensions.put(generateSchemaName, extensions); // 设置扩展属性
//            // 增加扩展属性
//            operation.addExtension(EXTENSION_ORANGE_FORM_NAME, orangeExtensions);
//            if (!ignoreFieldName.isEmpty()) { // 忽略字段名不为空
//                operation.addExtension(EXTENSION_ORANGE_FORM_IGNORE_NAME, ignoreFieldName); // 添加忽略的参数名
//            }
//        }
//        return operation;
//    }
//
//    /**
//     * 处理操作摘要
//     */
//    private void handleSummary(Operation operation, HandlerMethod handlerMethod) {
//        io.swagger.v3.oas.annotations.Operation operationAnno =
//                handlerMethod.getMethod().getAnnotation(io.swagger.v3.oas.annotations.Operation.class);
//        if (operationAnno == null || StrUtil.isBlank(operationAnno.summary())) { // 如果没有摘要或摘要为空
//            operation.setSummary(handlerMethod.getMethod().getName()); // 设置摘要为方法名
//        }
//    }
//
//    /**
//     * 处理参数详情
//     */
//    private void handleParameterDetail(
//            Parameter parameter,
//            Map<String, Object> properties,
//            Map<String, io.swagger.v3.oas.annotations.Parameter> paramMap,
//            Set<String> ignoreFieldName,
//            List<String> required) {
//        Class<?> parameterType = parameter.getType(); // 获取参数类型
//        String schemaName = parameterType.getSimpleName(); // 获取参数的简单名称
//        // 添加忽略参数名称
//        ignoreFieldName.addAll(getClassFields(parameterType));
//        // 处理 schema 注解别名的情况
//        Schema schema = parameterType.getAnnotation(Schema.class);
//        if (schema != null && StrUtil.isNotBlank(schema.name())) { // 存在 Schema 注解且别名不为空
//            schemaName = schema.name(); // 设置别名为 schema 名称
//        }
//        Map<String, Object> value = MapUtil.newHashMap(); // 参数值
//        // 此处需要判断 parameter 的基础数据类型
//        if (parameterType.isPrimitive() || parameterType.getName().startsWith("java.lang")) { // 如果是基本数据类型或者是 java.lang 包下的类
//            // 基础数据类型
//            ignoreFieldName.add(parameter.getName()); // 添加忽略的字段名
//            value.put("type", parameterType.getSimpleName().toLowerCase()); // 设置类型为简单类型
//        } else if (Collection.class.isAssignableFrom(parameterType)) { // 如果是集合类型
//            value.put("type", "array"); // 设置类型为数组
//            // 获取泛型
//            getGenericType(parameterType, parameter.getParameterizedType())
//                    .ifPresent(s -> value.put("items", MapUtil.builder(REF_KEY, REF_SCHEMA_PREFIX + s).build()));
//        } else { // 如果是引用类型
//            value.put(REF_KEY, REF_SCHEMA_PREFIX + schemaName); // 设置引用
//        }
//        // 补一个 description
//        io.swagger.v3.oas.annotations.Parameter paramAnnotation = paramMap.get(parameter.getName());
//        if (paramAnnotation != null) { // 参数注解不为空
//            // 忽略该参数
//            ignoreFieldName.add(paramAnnotation.name()); // 添加忽略的字段名
//            value.put("description", paramAnnotation.description()); // 设置描述
//            if (StrUtil.isNotBlank(paramAnnotation.example())) { // 如果示例不为空
//                value.put("default", paramAnnotation.example()); // 设置默认值为示例
//            }
//            // required 参数
//            if (paramAnnotation.required()) { // 如果参数为必须
//                required.add(parameter.getName()); // 添加到必须参数列表中
//            }
//        }
//        properties.put(parameter.getName(), value); // 设置参数属性
//    }
//
//    /**
//     * 获取泛型类型
//     */
//    private Optional<String> getGenericType(Class<?> clazz, Type type) {
//        Type genericSuperclass = clazz.getGenericSuperclass();
//        if (genericSuperclass instanceof ParameterizedType || type instanceof ParameterizedType) {
//            if (type instanceof ParameterizedType) {
//                genericSuperclass = type;
//            }
//            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
//            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
//            return Optional.of(((Class) actualTypeArguments[0]).getSimpleName());
//        }
//        return Optional.empty();
//    }
//
//    /**
//     * 获取类的字段名集合
//     */
//    private Set<String> getClassFields(Class<?> parameterType) {
//        if (parameterType == null) {
//            return Collections.emptySet();
//        }
//        if (cacheClassProperties.containsKey(parameterType)) {
//            return cacheClassProperties.get(parameterType);
//        }
//        Set<String> fieldNames = new HashSet<>();
//        try {
//            Field[] fields = parameterType.getDeclaredFields();
//            if (fields.length > 0) {
//                for (Field field : fields) {
//                    fieldNames.add(field.getName());
//                }
//                cacheClassProperties.put(parameterType, fieldNames);
//                return fieldNames;
//            }
//        } catch (Exception e) {
//            //ignore
//        }
//        return Collections.emptySet();
//    }
//
//    /**
//     * 获取参数描述
//     */
//    private Map<String, io.swagger.v3.oas.annotations.Parameter> getParameterDescription(Method method) {
//        Parameters parameters = method.getAnnotation(Parameters.class);
//        Map<String, io.swagger.v3.oas.annotations.Parameter> resultMap = MapUtil.newHashMap();
//        if (parameters != null) { // 如果参数不为空
//            io.swagger.v3.oas.annotations.Parameter[] parameters1 = parameters.value();
//            if (parameters1 != null && parameters1.length > 0) { // 参数数组不为空
//                for (io.swagger.v3.oas.annotations.Parameter parameter : parameters1) {
//                    resultMap.put(parameter.name(), parameter); // 添加到结果集中
//                }
//                return resultMap;
//            }
//        } else { // 参数为空
//            io.swagger.v3.oas.annotations.Parameter parameter =
//                    method.getAnnotation(io.swagger.v3.oas.annotations.Parameter.class);
//            if (parameter != null) {
//                resultMap.put(parameter.name(), parameter); // 添加到结果集中
//            }
//        }
//        return resultMap;
//    }
//
//}
