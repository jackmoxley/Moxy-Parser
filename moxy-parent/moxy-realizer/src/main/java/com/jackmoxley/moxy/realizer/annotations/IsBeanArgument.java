package com.jackmoxley.moxy.realizer.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jackmoxley.meta.Beta;
import com.jackmoxley.meta.ChangeLikelyhood;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD,ElementType.PARAMETER })
@Beta(ChangeLikelyhood.THIS_VERSION)
public @interface IsBeanArgument {
	String term() default "";
	String bean() default "";
	String value() default "";
	String transformer() default "";
}
