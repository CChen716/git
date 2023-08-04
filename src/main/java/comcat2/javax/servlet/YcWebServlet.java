package comcat2.javax.servlet;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})   //type表示这个注解智能放在类 接口....
@Retention(RetentionPolicy.RUNTIME)     //RUNTIME 表示这个注解再运行时还有

public @interface YcWebServlet {

    String value() default "";



}


