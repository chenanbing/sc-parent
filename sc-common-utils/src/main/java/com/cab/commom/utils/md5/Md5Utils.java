package com.cab.commom.utils.md5;

import com.google.common.collect.Ordering;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by by chenanbing on 2018/9/4 17:57.
 */
public class Md5Utils {

    /**
     * 获取加密
     */
    public static String sign(String content) {
        return sign(content, "UTF-8");
    }

    public static String sign(String content, String charset) {
        return sign(getContentBytes(content, charset));
    }

    public static String sign(byte[] bytes) {
        return DigestUtils.md5Hex(bytes);
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * 获取随机盐
     * @return
     */
    public static String getSalt() {
        String salt = RandomStringUtils.randomNumeric(8);
//        String salt = "admin" + "@gfcm";
        return sign(salt);
    }

    /**
     * @param content
     * @param salt
     * @return
     */
    public static String getSaltContent(String content, String salt) {
        String allString = content + salt;
        List<String> list = Arrays.asList(allString.split(""));

        Ordering<Object> usingToStringOrdering = Ordering.usingToString();
        List<String> getList = usingToStringOrdering.sortedCopy(list);
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : getList) {
            if (StringUtils.isEmpty(word))
                continue;
            stringBuilder.append(word);
        }

        return sign(stringBuilder.toString());
    }

    /**
     * 获取密码
     *
     * @param password
     * @return
     */
    public static String getPassword(String password) {
        return getSaltContent(password,getSalt());
    }

    public static void main(String[] args) {

//        System.out.println(sign("123456"));

    }

}
