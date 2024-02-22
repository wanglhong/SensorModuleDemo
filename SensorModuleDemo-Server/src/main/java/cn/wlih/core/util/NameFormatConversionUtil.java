package cn.wlih.core.util;

/**
 * 名称格式转换工具类
 */
public class NameFormatConversionUtil {

    /**
     * 大驼峰转小驼峰
     * @param str 需要转换的大驼峰字符串
     * @return 小驼峰字符串
     */
    public static String toLowerCaseFirstOne(String str){
        if (Character.isLowerCase(str.charAt(0))) {
            return str;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
        }
    }

    /**
     * 将驼峰命名转换为下划线命名
     * @param camelStr 驼峰命名
     * @return 下划线命名
     */
    public static String convertCamelToSnake(String camelStr) {
        // 如果输入为空或只有一个字符，直接返回输入
        if (camelStr == null || camelStr.isEmpty()) {
            return camelStr;
        }
        StringBuilder builder = new StringBuilder();
        // 将首字符处理为小写，确保结果符合规范
        builder.append(Character.toLowerCase(camelStr.charAt(0)));
        // 从第二个字符开始遍历
        for (int i = 1; i < camelStr.length(); i++) {
            char c = camelStr.charAt(i);
            // 如果是大写字符，则在前面添加下划线，并转换为小写
            if (Character.isUpperCase(c)) {
                builder.append('_').append(Character.toLowerCase(c));
            } else {
                // 如果不是大写字符，直接添加
                builder.append(c);
            }
        }
        return builder.toString();
    }

}
