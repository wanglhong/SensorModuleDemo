import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 王立宏
 * 描述:
 * path: SensorModuleDemo-PACKAGE_NAME-MyTest
 * date: 2023/8/31 11:20
 */
public class MyTest {

    @Test
    public void mapTest() {
        Map<String, Integer> map = new HashMap<>();
        int a = (int) map.get("test001");
        Integer b = Integer.valueOf(a) + 1;
        map.put("test001", b);
        Integer test001 = map.get("test001");
    }

    public static void main(String[] args) {
        List<String> dayList= new LinkedList<>();
        for (int i = 1; i <= 31; i++) {
            dayList.add("\"" + i + "号" + "\"");
        }
        System.out.println(dayList);
    }

}
