package com.scotia.sales.util;

public final class StringUtil {

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim()) ? true : false;
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !"".equals(str.trim()) ? true : false;
    }

    /**
     * 生成四位编号
     * @param str
     * @return
     */
    public static String formatCode(String str) {
        try {
            int length = str.length();
            Integer num = Integer.valueOf(str.substring(length - 4, length)) + 1;
            StringBuilder codeNum = new StringBuilder(num.toString());
            int codeLength = codeNum.length();
            for (int i = 4; i > codeLength; i--) {
                codeNum.append("0");
            }
            return codeNum.reverse().toString();
        } catch (NumberFormatException e) {
            return "0100";
        }
    }
}
