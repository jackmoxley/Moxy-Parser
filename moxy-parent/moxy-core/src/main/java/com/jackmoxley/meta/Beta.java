package com.jackmoxley.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface Beta {
	ChangeLikelyhood value() default ChangeLikelyhood.MAJOR_VERSION;
}
