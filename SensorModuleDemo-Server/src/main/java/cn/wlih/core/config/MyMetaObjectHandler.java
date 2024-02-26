//package cn.wlih.core.config;
//
//import cn.wlih.core.base.model.dbEnum.IsDeleteEnum;
//import cn.wlih.core.myEnum.DbBaseFieldType;
//import cn.wlih.core.util.MyClazzUtil;
//import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.reflection.MetaObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import supie.common.sequence.wrapper.IdGeneratorWrapper;
//
//import java.lang.reflect.Field;
//import java.util.Date;
//
//@Slf4j
//@Component
//public class MyMetaObjectHandler implements MetaObjectHandler {
//
//    @Autowired
//    private IdGeneratorWrapper idGeneratorWrapper;
//
//    /**
//     * 插入元对象字段填充（用于插入时对公共字段的填充）
//     *
//     * @param metaObject 元对象
//     */
//    @Override
//    public void insertFill(MetaObject metaObject) {
//        Date nowData = new Date();
//        // TODO 登录用户ID
//        Long loginUserId = 1001L;
//        Object originalObject = metaObject.getOriginalObject();
//        // 新增，需要设置 ID、创建时间、创建人ID、逻辑删除
//        MyClazzUtil.setDbBaseFieldValue(originalObject, DbBaseFieldType.ID, idGeneratorWrapper.nextLongId());
//        MyClazzUtil.setDbBaseFieldValue(originalObject, DbBaseFieldType.CREATE_TIME, nowData);
//        MyClazzUtil.setDbBaseFieldValue(originalObject, DbBaseFieldType.CREATE_USER_ID, loginUserId);
//        // 设置修改时间、修改人ID
//        MyClazzUtil.setDbBaseFieldValue(originalObject, DbBaseFieldType.UPDATE_TIME, nowData);
//        MyClazzUtil.setDbBaseFieldValue(originalObject, DbBaseFieldType.UPDATE_USER_ID, loginUserId);
//        MyClazzUtil.setDbBaseFieldValue(originalObject, DbBaseFieldType.IS_DELETE, IsDeleteEnum.EXIST);
//    }
//
//    /**
//     * 更新元对象字段填充（用于更新时对公共字段的填充）
//     *
//     * @param metaObject 元对象
//     */
//    @Override
//    public void updateFill(MetaObject metaObject) {
//        Date nowData = new Date();
//        // TODO 登录用户ID
//        Long loginUserId = 1001L;
//        Object originalObject = metaObject.getOriginalObject();
//        // 设置修改时间、修改人ID
//        MyClazzUtil.setDbBaseFieldValue(originalObject, DbBaseFieldType.UPDATE_TIME, nowData);
//        MyClazzUtil.setDbBaseFieldValue(originalObject, DbBaseFieldType.UPDATE_USER_ID, loginUserId);
//    }
//}
