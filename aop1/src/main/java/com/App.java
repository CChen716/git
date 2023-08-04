package com;

import com.yc.Config;
import com.yc.biz.OrderBiz;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext ac=new AnnotationConfigApplicationContext(Config.class);
        OrderBiz ob=ac.getBean(OrderBiz.class);
//        ob.makeOrder(1,99);
//        ob.findOrderId("apple");
//        ob.findOrderId("apple");
//        ob.findOrderId("pear");
//        ob.makeOrder(1,9999);
        ob.findOrderId("apple");
        ob.findPid("apple");
    }
}
