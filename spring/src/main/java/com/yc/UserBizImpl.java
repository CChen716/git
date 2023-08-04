package com.yc;

import org.ycframework.annotation.YcResource;
import org.ycframework.annotation.YcService;

import javax.annotation.Resource;

@YcService
public class UserBizImpl implements UserBiz{
    @YcResource(name = "userDaoImpl")
    private UserDao userDao;
    public void add(String uname){
        userDao.add(uname);
    }
}
