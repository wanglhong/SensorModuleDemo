import cn.wlih.core.base.model.dbEnum.IsDeleteEnum;
import cn.wlih.sys.model.SysUser;
import org.junit.jupiter.api.Test;

/**
 * 描述:
 *
 * @author 王立宏
 * @date 2023/9/28 15:32
 * @path SensorModuleDemo-PACKAGE_NAME-TestDemo01
 */
public class TestDemo01 {

    @Test
    public void test01() {
        SysUser sysUser = new SysUser();
        sysUser.setId(1001L);
        sysUser.setPassword("密码001");
        sysUser.setLoginName("小浣熊");
        sysUser.setIsDelete(IsDeleteEnum.EXIST);
        sysUser.setPhoneNumber("18888888888");

        System.out.println(sysUser);
        System.out.println(sysUser.getIsDelete().getDisplay());
    }

}
