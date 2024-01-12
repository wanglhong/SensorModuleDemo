package cn.wlih.app.controller;

import cn.wlih.app.model.Message;
import cn.wlih.app.service.SseTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admin/app/sse")
public class SseTestController {

    @Autowired
    private SseTestService sseTestService;

    /**
     * 定时执行 秒 分 时 日 月 周
     */
    @Scheduled(cron = "*/3 * * * * *")  // 间隔5秒
    public void sendMessageTask() {
        Message message = new Message();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        message.setData(LocalDateTime.now().format(format));
        sseTestService.sendMessage(message);
    }

    /**
     * SSE测试 发送
     *
     * @return
     */
    @GetMapping(path = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect() {
        String uuid = UUID.randomUUID().toString();
        log.info("新用户连接：{}", uuid);
        return sseTestService.connect(uuid);
    }
    /**
     * 广播消息
     *
     * @param message
     */
    @ResponseBody
    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody Message message) {
        sseTestService.sendMessage(message);
    }

    private String msg = "1月2日，新年第一个工作日，央视《新闻联播》播发系列报道介绍，解放军全军官兵聚力备战打仗，为如期实现建军一百年奋斗目标团结奋斗。\n" +
            "\n" +
            "\n" +
            "　　报道中，来自空军、陆军、海军的各型装备、演训画面被披露。\n" +
            "\n" +
            "\n" +
            "　　政知君注意到，其中，我国第三艘航空母舰福建舰出镜。相比2022年福建舰下水画面，其电磁弹射装置上的施工棚已经拆除，三条弹射轨道清晰可见。\n" +
            "\n" +
            "\n" +
            "　　根据《新闻联播》画面，身着戎装、佩戴上校领章的海军福建舰尹洪新表示——\n" +
            "\n" +
            "　　我们始终牢记主席重托，奋力投身航母事业，按计划稳步推进系泊试验。新的一年，我们将只争朝夕、顽强奋斗，力争早日形成战斗力，向着如期实现建军一百年奋斗目标奋力前行。\n" +
            "\n" +
            "　　资料显示，舷号“18”的福建舰是我国第三艘航母，自主研发制造，采用平直通长飞行甲板，配置电磁弹射和阻拦装置，满载排水量8万余吨，于2022年6月下水。\n" +
            "\n" +
            "\n" +
            "　　2023年11月30日，国防部新闻发言人吴谦应询回应称，福建舰目前正在开展系泊试验，后续将按计划稳步推进建设项目。\n" +
            "\n" +
            "　　军事评论员宋忠平向政知君介绍，一般而言，航母的建造流程分为几个重要节点：立项、开工（分段建造后拼装为整体）、下水、舾装、系泊、海试。\n" +
            "\n" +
            "\n" +
            "　　可以看出，系泊试验已经距离航母出海测试、入列服役不远。“所谓系泊试验，可以类比为硬装、软装结束后的精装和测试阶段。即让航母停靠在码头岸边，对已经安装的动力、通讯、电子等系统进行单独和联动测试。”宋忠平解读称。\n" +
            "\n" +
            "　　对比早前我国前两艘航母辽宁舰、山东舰的建造、服役流程，福建舰下水后的官方报道中，“舾装”这一重要节点不再出现——\n" +
            "\n" +
            "　　山东舰下水后，央视称，后续将按计划进行设备调试、舾装施工，并开展系泊试验。\n" +
            "\n" +
            "　　福建舰下水后，央视称，将按计划进行系泊试验和航行试验。\n" +
            "\n" +
            "　　宋忠平就此表示，这说明福建舰在船坞建造过程中，很多系统、装备已经同步进行安装，大大简化甚至跳过了下水后的舾装环节，为直接开展上述系泊试验创造条件，节约了工期。“能够‘事半功倍’，一是由于我国在大型水面舰艇建造方面取得长足进步；另一方面也表明，我们的科研、施工人员跳出既有模式，依靠自己的智慧和经验实现了航母建造流程的优化。值得为军工人点赞。”\n" +
            "\n" +
            "　　一个月前，去年12月1日，环球网发文援引专家观点称，根据媒体近期发布的照片，福建舰附近出现一艘“保姆船”，这表明随船测试的技术人员已经到位，福建舰的海试航行测试或将在不久后进行。\n" +
            "\n" +
            "责任编辑：陈建瑞 SN243";

}
