package com.yc.cglibproxy;


import com.yc.jdkproxy.JdkProxyTool;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CglibProxyTool implements MethodInterceptor {

    private Object target;

    public CglibProxyTool(Object target){
        this.target=target;
    }

    //生成代理对象的方法
    public Object createProxy(){
//        Object proxy= Proxy.newProxyInstance(JdkProxyTool.class.getClassLoader(),
//                target.getClass().getInterfaces(),
//                this);
      //  等同于


        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        Object proxy=enhancer.create();
        return proxy;
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {


        if (method.getName().startsWith("add")){
            showHello();//加入前置增强
        }
        Object returnValue=method.invoke(target,objects);  //调用目标类的方法
        return returnValue;
    }

    private void showHello() {
        System.out.println("hello");
    }
}
