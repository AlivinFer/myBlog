package com.alivin.myblog.utils;

import com.alivin.myblog.constant.WebConst;
import com.github.pagehelper.PageInfo;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公共函数
 *
 * @author Fer
 * @date 2021/8/14
 */
@Component
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

    /**
     * 返回github头像地址
     *
     * @param email 邮箱
     * @return
     */
    public static String gravatar(String email) {
        String avatarUrl = "https://github.com/identicons/";
        if (StringUtils.isBlank(email)) {
            email = "user@hanshuai.xin";
        }
        String hash = TaleUtils.MD5encode(email.trim().toLowerCase());
        return avatarUrl + hash + ".png";
    }

    /**
     * 网站链接
     *
     * @return
     */
    public static String site_url() {
        return site_url("");
    }

    /**
     * 返回网站链接下的全址
     *
     * @param sub 后面追加的地址
     * @return
     */
    public static String site_url(String sub) {
        return site_option("site_url") + sub;
    }


    /**
     * 网站配置项
     *
     * @param key
     * @return
     */
    public static String site_option(String key) {
        return site_option(key, "");
    }

    /**
     * 网站配置项
     *
     * @param key
     * @param defalutValue 默认值
     * @return
     */
    public static String site_option(String key, String defalutValue) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        String str = WebConst.initConfig.get(key);
        if (StringUtils.isNotBlank(str)) {
            return str;
        } else {
            return defalutValue;
        }
    }

    /**
     * 判断分页中是否有数据
     */
    public static boolean is_empty(PageInfo paginator) {
        return paginator == null || (paginator.getList() == null) || (paginator.getList().size() == 0);
    }

    /**
     * 截取字符串
     */
    public static String substr(String str, int len) {
        if (str.length() > len) {
            return str.substring(0, len);
        }
        return str;
    }

    /**
     * 格式化unix时间戳为日期
     */
    public static String fmtdate(Integer unixTime) {
        return fmtdate(unixTime, "yyyy-MM-dd");
    }

    /**
     * 格式化unix时间戳为日期
     */
    public static String fmtdate(Integer unixTime, String patten) {
        if (null != unixTime && StringUtils.isNotBlank(patten)) {
            return DateKit.formatDateByUnixTime(unixTime, patten);
        }
        return "";
    }

    /**
     * 英文格式的日期
     */
    public static String fmtdate_en(Integer unixTime){
        String fmtdate = fmtdate(unixTime, "yyyy, MM, dd");
        String[] dateArr = fmtdate.split(",");
        String rs = "<span>" + dateArr[0] + "</span> " + dateArr[1] + "  " + dateArr[2];
        return rs;
    }

    /**
     * 英文格式的日期-月，日
     */
    public static String fmtdate_en_m(Integer unixTime){
        return fmtdate(unixTime,"MMM d");
    }

    /**
     * 日期-年
     */
    public static String fmtdate_en_y(Integer unixTime){
        return fmtdate(unixTime,"yyyy");
    }

    /**
     * 字符串转Date
     */
    public static Date fmtdate_date(String date){
        if (StringUtils.isNotBlank(date)){
            return DateKit.dateFormat(date, "yyyy年MM月");
        }
        return null;
    }

    /**
     * 根据unix时间戳获取Date
     */
    public static Date fmtdate_unxtime(Integer nuixTime){
        if (null != nuixTime){
            return DateKit.getDateByUnixTime(nuixTime);
        }
        return  null;
    }

    /**
     * 获取社交的链接地址
     */
    public static Map<String, String> social() {
        final String prefix = "social_";
        Map<String, String> map = new HashMap<>();
        map.put("csdn", WebConst.initConfig.get(prefix + "csdn"));
        map.put("jianshu", WebConst.initConfig.get(prefix + "jianshu"));
        map.put("resume", WebConst.initConfig.get(prefix + "resume"));
        map.put("weibo", WebConst.initConfig.get(prefix + "weibo"));
        map.put("zhihu", WebConst.initConfig.get(prefix + "zhihu"));
        map.put("github", WebConst.initConfig.get(prefix + "github"));
        map.put("twitter", WebConst.initConfig.get(prefix + "twitter"));
        return map;
    }

    /**
     * 返回作品文章地址
     * @param cid
     * @return
     */
    public static String photoPermalink(Integer cid) {
        return site_url("/photo/article/" + cid.toString());
    }

    /**
     * 返回blog文章地址
     * @param cid
     * @return
     */
    public static String blogPermalink(Integer cid) {
        return site_url("/blog/article/" + cid.toString());
    }

    /**
     * 获取网站标题
     * @return
     */
    public static String site_title() {
        return site_option("site_title");
    }

    /**
     * 获取google网站验证码
     */
    public static String google_site_verification(){
        return site_option("google_site_verification");
    }

    /**
     * 获取百度网站验证码
     */
    public static String baidu_site_verification(){
        return site_option("baidu_site_verification");
    }

    /**
     * 获取网站的备案信息
     */
    public static String site_record() {
        return site_option("site_record");
    }

    /**
     * 获取文章第一张图片
     */
    public static String show_thumb(String content) {
        content = TaleUtils.mdToHtml(content);
        if (content.contains("<img")) {
            String img = "";
            String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
            Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
            Matcher m_image = p_image.matcher(content);
            if (m_image.find()) {
                img = img + "," + m_image.group();
                // //匹配src
                Matcher m = Pattern.compile("src\\s*=\\s*\'?\"?(.*?)(\'|\"|>|\\s+)").matcher(img);
                if (m.find()) {
                    return m.group(1);
                }
            }
        }
        return "";
    }

    /**
     * 如果blog没有配图，随机获取一张
     */
    public static String randomBlogPic(Long seed){
        return "/site/images/blog-images/blog-" + random( seed,12,".jpg");
    }

    /**
     * 获取文章中所有的文字
     */
    public static List<String> show_all_p(String content){
        List<String> rs = new LinkedList();
        content = TaleUtils.mdToHtml(content);
        String reg = "<[a-zA-Z]+.*?>([\\s\\S]*?)</[a-zA-Z]*>";

        Pattern p = Pattern.compile(reg, Pattern.MULTILINE);
        content = content.replace("&nbsp;", "");
        Matcher m = p.matcher(content);
        while(m.find()) {
            String data = m.group(1).trim();
            if(!"".equals(data) && !data.contains("<img")) {
                System.out.println(data);
                data = "<p>" + data + "</p>";
                rs.add(data);
            }
        }
        return rs;
    }

    /**
     * 获取文章中的所有图片
     */
    public static List<String> show_all_thumb(String content) {
        List<String> rs = new LinkedList<>();
        content = TaleUtils.mdToHtml(content);
        if (content.contains("<img")) {
            String img = "";
            String regEx_img = "<[a-zA-Z]+.*?>([\\s\\S]*?)</[a-zA-Z]*>";
            Pattern p_image = Pattern.compile(regEx_img, Pattern.MULTILINE);
            Matcher m_image = p_image.matcher(content);
            while (m_image.find()) {
                String data = m_image.group(1).trim();
                if(!"".equals(data) && data.contains("<img")) {
                    System.out.println(data);
                    // //匹配src
                    Matcher m = Pattern.compile("src\\s*=\\s*\'?\"?(.*?)(\'|\"|>|\\s+)").matcher(data);
                    while (m.find()){
                        //  if (m.find()) {
                        rs.add(m.group(1));
                    }
                }

            }
        }
        return rs;
    }

    /**
     * 显示文章内容，转换markdown为html
     *
     * @param value
     * @return
     */
    public static String article(String value) {
        if (StringUtils.isNotBlank(value)) {
            value = value.replace("<!--more-->", "\r\n");
            value = value.replace("<!-- more -->", "\r\n");
            return TaleUtils.mdToHtml(value);
        }
        return "";
    }
}
