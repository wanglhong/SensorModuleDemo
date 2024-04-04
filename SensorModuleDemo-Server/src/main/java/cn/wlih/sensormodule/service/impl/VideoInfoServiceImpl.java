package cn.wlih.sensormodule.service.impl;

import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import cn.wlih.sensormodule.dao.VideoInfoMapper;
import cn.wlih.sensormodule.model.VideoInfo;
import cn.wlih.sensormodule.service.VideoInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("videoInfoService")
public class VideoInfoServiceImpl extends MyBaseServiceImpl<VideoInfo> implements VideoInfoService {

    @Autowired
    private VideoInfoMapper videoInfoMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<VideoInfo> mapper() {
        return videoInfoMapper;
    }

}
