package org.ycframework.annotation;

import javax.annotation.Resource;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Type;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE,FIELD,METHOD})
@Retention(RUNTIME)
public @interface YcResource {
    String name()default "";


}
