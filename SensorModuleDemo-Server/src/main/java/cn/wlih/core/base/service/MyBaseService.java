package cn.wlih.core.base.service;

import java.util.Map;

public interface MyBaseService<M> {

    Map<String, String> getModelJson(Class<M> modelClass);

    /**
     * 新增数据
     * @param m 需要新增的数据
     * @return 成功返回true，失败返回false
     */
    Boolean add(M m);
}
