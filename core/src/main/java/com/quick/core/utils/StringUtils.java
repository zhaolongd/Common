package com.quick.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/12 0012 15:09
 */
public class StringUtils {
    /**
     * 判断字符串是否为空或空字符
     *
     * @param strSource 源字符串
     * @return true表示为空，false表示不为空
     */
    public static boolean isNull(final String strSource) {
        return strSource == null || "".equals(strSource.trim());
    }

    public static String removeAllBank(String str) {
        String s = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            s = m.replaceAll("");
        }
        return s;
    }

    public static String removeAllBank(String str, int count) {
        String s = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s{" + count + ",}|\t|\r|\n");
            Matcher m = p.matcher(str);
            s = m.replaceAll(" ");
        }
        return s;
    }
}
