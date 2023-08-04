package com.yc.dao;

import com.yc.bean.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class AccountDaoJdbcTemplateImpl implements AccountDao{

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void init(DataSource dataSource){
        this.jdbcTemplate=new JdbcTemplate(dataSource);
    }



    @Override
    public int insert(double money) {
        KeyHolder keyHolder=new GeneratedKeyHolder();   //通过KeyHolder 取得生成的值
        jdbcTemplate.update(connection->{
            PreparedStatement ps=connection.prepareStatement("insert into accounts(balance) values(?)",new String[]{"accountid"});
            ps.setString(1,money+"");  // +"" 作用将参数`money`转化为字符串，并设置到占位符位置
            return ps;
        },keyHolder);
        return keyHolder.getKey().intValue(); // 使用`KeyHolder`对象来保存生成的主键值
                                                //通过`getKey`方法获取生成的主键值，并使用`intValue`方法将其转化为`int`类型并返回。
    }

    @Override
    public void update(int accountid, double money) {
            this.jdbcTemplate.update(
                    "update accounts set balance=balance+? where accountid=? ",
                    money+"",accountid+"");
    }

    @Override
    public void delete(int accountid) {
            this.jdbcTemplate.update(
                    "delete from accounts where accountid= ?",
                    Integer.valueOf(accountid)
            );
    }

    @Override
    public int findCount() {
        int rowCount=this.jdbcTemplate.queryForObject("select count(*) from accounts",Integer.class);
        return rowCount;
    }

    @Override
    public List<Account> findAll() {
        List<Account> list=jdbcTemplate.query(
               "select  * from accounts",
                (resultSet,rowNum)->{
                   Account a=new Account();
                   a.setAccountid(resultSet.getInt(1));
                   a.setMoney(resultSet.getDouble(2));
                   return a;
                });
        return list;

    }

    @Override
    public Account findById(int accountid) {
        //模版模式
        Account account=jdbcTemplate.queryForObject(
                "select * from accounts where accountid=?",
                (resultSet,rowNum)->{
                    Account a=new Account();
                    a.setAccountid(resultSet.getInt(1));
                    a.setMoney(resultSet.getDouble(2));
                    return a;
                },accountid);
        return account;
    }
}
