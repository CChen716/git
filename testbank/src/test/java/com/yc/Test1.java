package com.yc;

import com.yc.biz.AccountBiz;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class Test1 extends TestCase {
    //可以在这里完成DI
    @Autowired
    private AccountBiz accountBiz;



    //引入断言
    @Test
    public void  testAdd(){
        int x=30,y=4;
        Assert.assertEquals(7,7);   //左边是期望值   右边是实际值
    }
}
