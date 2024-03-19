package cn.wlih.upms.controller;

import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.upms.dto.SysDeptDto;
import cn.wlih.upms.model.SysDept;
import cn.wlih.upms.service.SysDeptService;
import cn.wlih.upms.vo.SysDeptVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "系统部门管理")
@RequestMapping("/api/upms/dept")
public class SysDeptController extends MyBaseController<SysDept, SysDeptDto, SysDeptVo> {

    @Autowired
    private SysDeptService sysDeptService;

}
