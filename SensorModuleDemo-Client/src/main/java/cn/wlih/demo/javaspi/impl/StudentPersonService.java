package cn.wlih.demo.javaspi.impl;

import cn.wlih.demo.javaspi.PersonService;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述：
 *
 * @author 王立宏
 * @date 2023/10/16 10:25
 * @path SensorModuleDemo-cn.wlih.demo.javaspi.impl-StudentPersonService
 */
@Slf4j
public class StudentPersonService implements PersonService {

    /**
     * 讲话
     *
     * @author 王立宏
     * @date 2023/10/16 10:22
     */
    @Override
    public void sayHello() {
        log.info("Hello, I am a student.");
    }
}
