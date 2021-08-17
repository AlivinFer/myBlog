package com.alivin.myblog.utils;

import com.vdurmont.emoji.EmojiParser;

import java.util.Random;

/**
 * @author Fer
 * @date 2021/8/14
 */
public class Commons {
    /**
     * An :grinning:awesome :smiley:string &#128516;with a few :wink:emojis!
     * <p>
     * 这种格式的字符转换为emoji表情
     */
    public static String emoji(String value) {
        return EmojiParser.parseToUnicode(value);
    }

    /**
     * 获取随机数
     *
     * @param max
     * @param str
     * @return
     */
    public static String random(int max, String str) {
        return UUID.random(1, max) + str;
    }

    public static String random(Long seed, int max, String str){
        if (seed == null)
            return random(max, str);
        Random random = new Random(seed);
        return random.nextInt(max) + str;
    }
}
