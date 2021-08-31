package com.mousse.enums;

/**
 * @author mousse
 * @data 2021/8/31
 */
public enum CommentTypeEnum {
    QUESTION(1), COMMENT(2);
    private final Integer type;
    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
