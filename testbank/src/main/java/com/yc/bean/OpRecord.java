package com.yc.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor    //带所有参数的构造方法
@NoArgsConstructor          //空参数的构造方法
@ToString               //生成toString()
public class OpRecord {
    private int id;
    private int accountid;
    private double opmoney;
    private String optime;    //数据库是datetime类型,在java中转成字符串   如果有时间加减需求 则不需要转
    private OpType opType;
    private Integer transferid;
}
