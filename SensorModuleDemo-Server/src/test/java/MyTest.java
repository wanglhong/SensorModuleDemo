import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * 描述:非SpringBoot自定义测试
 * @author 王立宏
 * path: SensorModuleDemo-PACKAGE_NAME-MyTest
 * date: 2023/8/31 11:20
 */
public class MyTest {

    @Test
    void test01() {
        List<String> dayList= new LinkedList<>();
        for (int i = 1; i <= 31; i++) {
            dayList.add("\"" + i + "号" + "\"");
        }
        System.out.println(dayList);
    }

}
