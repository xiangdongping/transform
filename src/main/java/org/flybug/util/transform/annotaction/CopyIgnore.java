package org.flybug.util.transform.annotaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用该注解表示将忽略
 * 某些值的拷贝
 * 当某些值不希望被拷贝的时候使用
 * 实际如果 a copy 到 b  a事例中的字段 在b中不存在本身也会被忽略a的拷贝
 * @author xdp
 * @date 2018/7/5
 */

@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CopyIgnore {

}
