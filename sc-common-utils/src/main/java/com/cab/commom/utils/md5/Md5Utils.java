package com.cab.commom.utils.md5;

import com.google.common.collect.Ordering;
import com.google.common.hash.Hashing;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by by chenanbing on 2018/9/4 17:57.
 */
public class Md5Utils {

    /**
     * 获取加密
     */
    public static String getMd5(String pwd) {
        String md = Hashing.md5().newHasher().putString(pwd, Charsets.UTF_8).hash().toString();
        return md;
    }

    public static String randomPassword() {
        String password = RandomStringUtils.randomNumeric(32);
        return getMd5(password);
    }

    /**
     * 获取随机盐
     *
     * @return
     */
    public static String getSalt() {
        String salt = RandomStringUtils.randomNumeric(8);
//        String salt = "admin" + "@gfcm";
        return getMd5(salt);
    }

    /**
     * 获取密码
     *
     * @param password
     * @param salt
     * @return
     */
    public static String getPassword(String password, String salt) {
        String allString = password + salt;
        List<String> list = Arrays.asList(allString.split(""));

        Ordering<Object> usingToStringOrdering = Ordering.usingToString();
        List<String> getList = usingToStringOrdering.sortedCopy(list);
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : getList) {
            if (StringUtils.isEmpty(word))
                continue;
            stringBuilder.append(word);
        }

        return getMd5(stringBuilder.toString());
    }

    public static void main(String[] args) {
    }

}
