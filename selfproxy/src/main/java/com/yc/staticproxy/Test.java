package com.yc.staticproxy;

public class Test {
    public static void main(String[] args) {
        OrderBiz ob=new StaticProxy(new OrderBizImpl());
        ob.addOrder(1,100);
    }
}
