package com.yc;

import com.yc.configs.DataSourceConfig;
import junit.framework.TestCase;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class, DataSourceConfig.class})
@Log4j2
public class Test2_DataSourceConfig extends TestCase {

    @Autowired
    private DataSourceConfig dsc; //方式一

    @Autowired
    private Environment env;  //方式二   数据从配置文件中读取后同时会存到spring中的  Environment中

    @Autowired
    @Qualifier("dataSource")
    private DataSource ds;

    @Autowired
    @Qualifier("dbcpDataSource")
    private DataSource dbcpDataSource;

    @Autowired
    @Qualifier("druidDataSource")
    private DataSource druidDataSource;   //性能最优越

    @Autowired
    private TransactionManager tx;

    @Test
    public void testTransactionManager(){
        log.info(tx);
    }


    @Test //方式一
    public void testPropertySource(){
        Assert.assertEquals("root",dsc.getUsername());
        log.info(dsc.getUsername());
    }

    @Test//方式二
    public void testEnvironment(){
        log.info(env.getProperty("mysql.password"));
    }

    @Test
    public void testDBCPConnection() throws SQLException {
        Assert.assertNotNull(ds.getConnection());
        Connection con=ds.getConnection();
        log.info(con);
    }

    @Test
    public void testDbcpConnection() throws SQLException {
        Assert.assertNotNull(dbcpDataSource.getConnection());
        Connection con=dbcpDataSource.getConnection();
        log.info(con);
    }

    @Test
    public void testDruidDataSource() throws SQLException {
        Assert.assertNotNull(druidDataSource.getConnection());
        Connection con=ds.getConnection();
        log.info(con);
    }



}
