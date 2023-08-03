package com.yc.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

//Serializable   此类的对象可以序列化到磁盘  标志接口
@Data
@AllArgsConstructor    //带所有参数的构造方法
@NoArgsConstructor          //空参数的构造方法
@ToString               //生成toString()
public class Account implements Serializable {
    private int accountid;
    private double money;
}
