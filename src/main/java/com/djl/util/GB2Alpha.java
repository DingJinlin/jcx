package com.djl.util;

import opensource.jpinyin.PinyinHelper;

/**
 * User: Ding
 * Date: 13-3-23
 * Time: 下午4:19
 */
public class GB2Alpha {
    //根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串
    public String String2Alpha(String chinese) {
        String shortPY = PinyinHelper.getShortPinyin(chinese);
        return shortPY.replaceAll("\\W", "").trim().toUpperCase();
    }


    public static void main(String[] args) {
        GB2Alpha obj1 = new GB2Alpha();
        System.out.println(obj1.String2Alpha("测试：中华人民共和国！"));
        System.out.println(obj1.String2Alpha("裴贺先A"));
    }
}
