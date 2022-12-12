package com.lll.poweradmin.common.enums;

public enum UserStatus {

    OK("0", "正常"),
    DISABLE("1", "停用"),
    DELETED("2", "删除");

    private final String code;
    private final String desc;

    UserStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
