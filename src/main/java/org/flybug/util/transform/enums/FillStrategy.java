package org.flybug.util.transform.enums;

/**
 * 转换时填充方式
 * Created by xdp on 2018/7/1.
 */
public enum  FillStrategy {

    /**
     * 源属性 目标属性
     */
    SOURCE_FIELD_TARGET_FILED,
    /**
     * 源方法 目标方法
     */
    SOURCE_METHOD_TARGET_METHOD,
    /**
     * 源属性 目标方法
     */
    SOURCE_FILED_TARGET_METHOD,
    /**
     * 源方法 目标属性
     */
    SOURCE_METHOD_TARGET_FILE


}
