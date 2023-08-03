package com.yc.bean;

//enum: 枚举   enumeration   相当于一个数组  但每个元素以key 和value形式存在
public enum OpType {

    WITHDRAW("withdrwa",1),
    DEPOSITE("deposite",2),
    TRANSFER("transfer",3);


    private String key;
    private int value;


    OpType(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
