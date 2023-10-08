import cn.wlih.core.base.BaseModel;
import lombok.Data;
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

    /**
     * 枚举测试
     *
     * @author 王立宏
     * @date 2023/09/28 12:45
     */
    @Test
    void enumTest() {
//        BaseModel baseModel = new BaseModel();
        IsDelete isDelete = IsDelete.EXIST;
        System.out.println(isDelete);
        IsDelete isDelete2 = IsDelete.DELETE;
        System.out.println(isDelete2);
    }

    @Test
    void test01() {
        List<String> dayList= new LinkedList<>();
        for (int i = 1; i <= 31; i++) {
            dayList.add("\"" + i + "号" + "\"");
        }
        System.out.println(dayList);
    }

}

enum IsDelete {
    EXIST(1), DELETE(2);
    private final Integer data;

    private IsDelete(Integer data) {
        this.data = data;
    }

    public Integer getData() {
        return data;
    }
}
