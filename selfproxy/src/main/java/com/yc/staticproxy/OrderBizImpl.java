package com.yc.staticproxy;

public class OrderBizImpl implements OrderBiz{
    @Override
    public void addOrder(int pid, int num) {
        System.out.println("添加订单,添加了:"+pid+",数量为:"+num);
    }

    @Override
    public void findOrder() {
        System.out.println("查询订单");
    }
}
