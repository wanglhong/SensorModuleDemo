package cn.wlih.core.base.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface MyBaseService<M> extends IService<M> {

    Map<String, String> getModelJson(Class<M> modelClass);

    /**
     * 新增数据
     * @param m 需要新增的数据
     * @return 成功返回true，失败返回false
     */
    M add(M m);

    /**
     * 查询所有数据
     * @return 返回查询结果
     */
    List<M> selectList(M m);

    Boolean removeByIdList(List<Long> idList);

    /**
     * 验证导入一对一关联数据
     */
    void verifyImportForOneToOneRelation(List<M> modelDataList);
    /**
     * 验证导入一对一关联数据
     */
    void verifyImportForOneToOneRelation(M modelDataList);

}
