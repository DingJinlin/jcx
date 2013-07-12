package com.djl.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * User: Ding
 * Date: 13-3-23
 * Time: 下午4:19
 */
public class GB2Alpha {
    //根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串
    public String String2Alpha(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : arr) {
            if (c > 128) {
                try {
                    String[] _t = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
                    if (_t != null) {
                        pybf.append(_t[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(c);
            }
        }

        return pybf.toString().replaceAll("\\W", "").trim();
    }


    public static void main(String[] args) {
        GB2Alpha obj1 = new GB2Alpha();
        System.out.println(obj1.String2Alpha("测试：中华人民共和国！"));
        System.out.println(obj1.String2Alpha("裴贺先A"));
    }
}
