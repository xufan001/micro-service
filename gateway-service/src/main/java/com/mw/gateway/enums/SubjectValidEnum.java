package com.mw.gateway.enums;

/**
 * 是否有效的枚举
 */
public enum SubjectValidEnum {
    VALID("有效",0),INVALID("无效",1);
    private String value;
    private Integer index;

    SubjectValidEnum(String value, Integer index){
        this.value = value;
        this.index=index;
    }

    public String getValue() {
        return value;
    }

    public Integer getIndex() {
        return index;
    }

    public String getStatus() {
        return value;
    }


}
