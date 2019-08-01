package com.cab.common.beans.database.enums;

/**
 * @Description: 用户状态
 * @Auther: Administrator
 * @Date: 2018/9/13
 */
public enum UserStatusEnum {

    normal(0, "正常"),
    forbid(1, "禁用"),
    del(2, "删除");
    // 成员变量
    private int code;
    private String name;
    // 构造方法
    private UserStatusEnum(int code, String name) {
        this.name = name;
        this.code = code;
    }

    // 普通方法
    public static String getNameByCode(int code) {
        for (UserStatusEnum e : UserStatusEnum.values()) {
            if (e.getCode()==code) {
                return e.name;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
