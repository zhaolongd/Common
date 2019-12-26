package com.quick.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/12 0012 15:09
 */
public class StringUtils {
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
