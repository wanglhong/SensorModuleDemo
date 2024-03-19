package cn.wlih.sys.controller;

import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.sys.model.SysDept;
import cn.wlih.sys.service.SysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/app/dept")
public class SysDeptController extends MyBaseController<SysDept> {

    @Autowired
    private SysDeptService sysDeptService;

}
