package cn.wlih.core.myEnum;

/**
 * 数据库基础字段的类型
 */
public enum DbBaseFieldType {
    ID("主键ID"),
    CREATE_TIME("创建时间"),
    CREATE_USER_ID("创建者ID"),
    UPDATE_TIME("修改时间"),
    UPDATE_USER_ID("修改者ID"),
    DATA_USER_ID("数据所属人ID"),
    DATA_DEPT_ID("数据所属部门ID"),
    IS_DELETE("逻辑删除（1：存在。-1：逻辑删除）");

    private final String fieldComment;

    DbBaseFieldType(String fieldComment) {
        this.fieldComment = fieldComment;
    }

    public String getFieldComment() {
        return fieldComment;
    }

}
