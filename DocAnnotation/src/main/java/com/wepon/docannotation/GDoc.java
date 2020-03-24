package com.wepon.docannotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface GDoc {

    String name() default "";

    String author() default "";

    String time() default "";
}
