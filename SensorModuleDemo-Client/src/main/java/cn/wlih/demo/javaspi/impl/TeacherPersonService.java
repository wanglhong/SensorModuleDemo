package cn.wlih.demo.javaspi.impl;

import cn.wlih.demo.javaspi.PersonService;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述：
 *
 * @author 王立宏
 * @date 2023/10/16 10:26
 * @path SensorModuleDemo-cn.wlih.demo.javaspi.impl-TeacherPersonService
 */
@Slf4j
public class TeacherPersonService implements PersonService {

    /**
     * 讲话
     *
     * @author 王立宏
     * @date 2023/10/16 10:22
     */
    @Override
    public void sayHello() {
        log.info("Hello, I am a teacher.");
    }
}
