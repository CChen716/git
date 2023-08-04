package com.yc;

import org.ycframework.annotation.YcResource;
import org.ycframework.annotation.YcService;

@YcService("ub")
public class UserDaoImpl implements UserDao{
    @YcResource(name = "userDaoImpl")
    private UserDao userDao;
    public void add(String uname){
        System.out.println("dao添加了"+uname);
    }
}
