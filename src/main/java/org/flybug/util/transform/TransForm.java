package org.flybug.util.transform;

import java.util.List;
import java.util.Map;

/**
 * Created by xdp on 2018/7/1.
 */
public interface TransForm {

    /**
     * 根据源类型 ， 创建目标类型实例并
     * 将原来的类型填充到目标类型
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    <T> T transform(Object source,Class<T> target);


    /**
     * 将原类型填充到目标类型
     * 默认忽略源类型 中null 值对目标类型的填充
     * @param source
     * @param target
     */
    void fill(Object source,Object target);


    /**
     * 将原类型填充到目标类型
     *
     * @param source 源对象实例
     * @param target 目标对象实例
     * @param ignoreNull 是否忽略空对象对于目标类型填充
     */
    void fill(Object source,Object target,boolean ignoreNull);

    /**
     * 将目标对象 转换成map 对象
     *
     * 例如有 Student{ private String name="张三"} 将会
     * 是 [{"name":"张三"}]
     * @param source
     * @return
     */
    List<Map<String,Object>> transformToMap(Object source);

}
