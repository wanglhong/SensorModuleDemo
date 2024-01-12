package cn.wlih.app.service;

import cn.wlih.app.model.Message;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseTestService {
    SseEmitter connect(String uuid);

    void sendMessage(Message message);
}
