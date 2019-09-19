package com.twiceyuan.retrokv.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 额外的 key 参数。
 *
 * 在存储一个 Key Value 时，Key 除了原始定义外还可以通过该类注解参数，在某些场景比较有用。
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface KeyParam {
    String value();
}
