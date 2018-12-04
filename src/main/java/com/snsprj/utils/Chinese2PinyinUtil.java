package com.snsprj.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉字转拼音工具类
 *
 * @author xiaohb
 */
public class Chinese2PinyinUtil {

    private static HanyuPinyinOutputFormat hanyuPinyinOutputFormat = null;

    public enum Type {
        UPPERCASE, // 全部大写
        LOWERCASE, // 全部小写
        FIRSTUPPER // 首字母大写
    }

    static {
        hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
        hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    public static String toPinYin(String str) throws BadHanyuPinyinOutputFormatCombination {
        return toPinYin(str, "", Type.UPPERCASE);
    }

    public static String toPinYin(String str, String spera)
            throws BadHanyuPinyinOutputFormatCombination {
        return toPinYin(str, spera, Type.UPPERCASE);
    }

    /**
     * 将str转换成拼音，如果不是汉字或者没有对应的拼音，则不作转换 如： 明天 转换成 MINGTIAN
     *
     * @param type: 字母大消息
     */
    public static String toPinYin(String str, String spera, Type type)
            throws BadHanyuPinyinOutputFormatCombination {

        if (str == null || str.trim().length() == 0) {
            return "";
        }

        if (type == Type.UPPERCASE) {
            hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        } else {
            hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        }

        String py = "";
        String temp = "";
        String[] t;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((int) c <= 128) {
                py += c;
            } else {
                t = PinyinHelper.toHanyuPinyinStringArray(c, hanyuPinyinOutputFormat);
                if (t == null) {
                    py += c;
                } else {
                    temp = t[0];
                    if (type == Type.FIRSTUPPER) {
                        temp = t[0].toUpperCase().charAt(0) + temp.substring(1);
                    }
                    py += temp + (i == str.length() - 1 ? "" : spera);
                }
            }
        }
        return py.trim();
    }

    public static void main(String[] args) {

        try {
            String pinyin = toPinYin("\uD83D\uDE0A宝哥BaoGG哈哈哈");
            System.out.println(pinyin);

        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
    }
}
