package cn.wlih.core.base.service.impl;

import cn.wlih.core.base.service.MyBaseService;
import cn.wlih.core.util.dbUtil.DbTableUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public abstract class MyBaseServiceImpl<M> implements MyBaseService<M> {

    @Override
    public Map<String, String> getModelJson(Class<M> modelClass) {
        List<Map<String, String>> classFieldMapList = new LinkedList<>();
        DbTableUtil.collectDbFields(modelClass, null, classFieldMapList);
        Map<String, String> resultData = new LinkedHashMap<>();
        for (Map<String, String> classFieldMap : classFieldMapList) {
            resultData.put(classFieldMap.get("fieldName"),
                    classFieldMap.get("fieldComment") + " —— " + classFieldMap.get("fieldType"));
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
    public Boolean add(M m) {
        log.info("BaseService新增数据");
        return true;
    }

}
