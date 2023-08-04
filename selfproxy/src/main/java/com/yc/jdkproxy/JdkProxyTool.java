package com.yc.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyTool implements InvocationHandler {

    //目标类
    private Object target;

    public JdkProxyTool(Object target){
        this.target=target;
    }

    //生成代理对象的方法
    public Object createProxy(){
        Object proxy= Proxy.newProxyInstance(JdkProxyTool.class.getClassLoader(),
                target.getClass().getInterfaces(),
                this);
        return proxy;
    }


    //当在主程序中调用生成的Proxy的中的方法, 会自动回调这个invoke() 在这个invoke() 加入增强  切面这些功能
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //以反射的方式读取下面的@Pointcut的切入点表达式  =》在spring底层 用aspectj来读取表达式
        if (method.getName().startsWith("add")){
            showHello();//加入前置增强
        }
        //相当于      orderBizImpl.findOrder()
        Object returnValue=method.invoke(target,args);  //调用目标类的方法
        return returnValue;
    }

    private void showHello() {
        System.out.println("hello");
    }
}
