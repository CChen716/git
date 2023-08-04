package com.yc.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Aspect
public class MyAspects {
    //切入点表达式： 正则表达式  筛选 目标类中那些方法加增强
    //  *：
    //.. :代表 0或n
     @Pointcut("execution(* com.yc.biz.*.make*(..))")
    private void a(){}

    @Before("a()")
  //  @Before("execution(* com.yc.biz.*.make*(..))")  两种写法效果一样
    public void recordTime(){
        LocalTime localTime=LocalTime.now();
        System.out.println("----------下单的时间:"+localTime);
    }

    @AfterReturning("a()")
    public void sendEmail(){
        System.out.println("调用数据库查询此下单的用户的email，包装信息，发消息送到中间件  kafka/mq  .");
    }

    @AfterReturning("a()")
    public void  recordParams(JoinPoint jp){ //记录连接点 make*() 中的参数信息    make*()就称为   JoinPoint
         //从jp中可以取出一些信息   make*()方法的信息
        System.out.println("增强的方法为:"+jp.getSignature());
        System.out.println("增强的目标类为："+jp.getTarget());
        System.out.println("参数:");
        Object [] params=jp.getArgs();
        for (Object o : params) {
            System.out.println(o);
        }
    }
    @Pointcut("execution(* com.yc.biz.*.findOrderId(String))")
    private void b(){}

    //TODO  正常是要访问redis 商品名  次数
    private Map<String,Long> map=new ConcurrentHashMap<>();
     //统计每个商品被查询的次数
    @AfterReturning("b()")
    public void recordPnameCount(JoinPoint jp){
        Object [] objs=jp.getArgs();
        String pname= (String) objs[0];
        Long num=1L;
        if (map.containsKey(pname)){
            num=map.get(pname);
            num++;
        }
        map.put(pname,num);
        System.out.println("统计结果:"+map);
    }

    @Pointcut("execution(int com.yc.biz.*.findPid(String))")
    private void c(){}
    private Map<String,Long> map2=new ConcurrentHashMap<>();

    //统计每个  商品名编号  被查询的次数
    @AfterReturning(pointcut = "c()",returning = "retValue")
    public void recordPnameCount2(JoinPoint jp,int retValue){  //DI方式注入
        Object [] objs=jp.getArgs();
        String pname= (String) objs[0];
        Long num=1L;
        if (map2.containsKey(pname)){
            num=map2.get(pname);
            num++;
        }
        map2.put(pname,num);
        System.out.println("统计结果:"+map2);
    }


    ///////////////对异常进行处理 ////////
    @AfterThrowing(pointcut = "a()",throwing = "ex")
    public void recordException(JoinPoint jp,RuntimeException ex){ //由spring容器捕捉的异常传入
        System.out.println("*********异常了******");
        System.out.println(ex.getMessage());
        System.out.println(jp.getArgs()[0]+"\t"+jp.getArgs()[1]);
        System.out.println("****************");

    }

    //////////////////查询方法特别慢  想统计一下时长  查询方法是find*
    @Pointcut("execution(* com.yc.biz.*.find*(..))")
    private void d(){}

    @Around("d()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        long start=System.currentTimeMillis();
        Object retVal=pjp.proceed();  //返回值 find*()
        long end=System.currentTimeMillis();
        System.out.println("方法执行时长为:"+(end-start));
        return retVal;
    }



}
