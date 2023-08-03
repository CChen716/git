#银行  存 取 事务管理

#创建库

create database testBank;
user testBank;

#账号信息表    auto_increment:  java的UUID-字符串
create table accounts
(
    accountid int primary  key auto_increment,
    balance numeric(10,2)
);

#创建流水表
(
    id int primary key auto_increment,
    accountid int references accounts(accountid),
    opmoney numeric(10,2),
    optime datetime,
    optype enum('deposite','withdraw','transfer') not null,
    transferid varchar(50)
);