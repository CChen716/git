package com.yc;

import org.ycframework.annotation.YcRepository;

@YcRepository
public interface UserDao {
    public void add(String uname);
}
