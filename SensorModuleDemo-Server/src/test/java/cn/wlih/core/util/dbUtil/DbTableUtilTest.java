package cn.wlih.core.util.dbUtil;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DbTableUtilTest {

    /**
     * 生成数据库建表语句
     */
    @Test
    void constructCreateTableSql() {
        String constructCreateTableSql = DbTableUtil.constructCreateTableSql("cn.wlih");
        System.out.println(constructCreateTableSql);
    }

}