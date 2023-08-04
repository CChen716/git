package com.yc.configs;


import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbcp2.BasicDataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
@Data  //lomhbok创建 get/set
@Log4j2
@EnableTransactionManagement     //启用事务管理器
public class DataSourceConfig {
    //利用Di将db.properties的内容注入
    @Value("${mysql.username}")
    private String username;
    @Value("${mysql.password}")
    private String password;
    @Value("${mysql.url}")
    private String url;
    @Value("${mysql.driverclass}")
    private String driverclass;
    //以上属性从db.properties中读取出来后  都存到了spring容器的  Environment的变量   系统环境变量也在这里

    @Value("#{T(Runtime).getRuntime().availableProcessors()*2}")  //可以运行这个里面的代码
    //spEl-> spring expression language
    private int cpuCount;

    @Bean
    public TransactionManager dataSourceTransactionManager(@Autowired  DataSource ds){
        DataSourceTransactionManager tx=new DataSourceTransactionManager();
        tx.setDataSource(ds);
        return tx;
    }


    @Bean(initMethod = "init",destroyMethod = "close") //DruidDataSource中提供聊 init初始化方法   //推荐使用durid数据源
    @Primary         //这里一共有三个DataSource     @Autowired无法识别是哪个  解决方法一 在后面加@Qualifier命名   方法二给其中一个加@Primary
    public DruidDataSource druidDataSource(){ //idea会对这个方法的返回值进行解析，判断是否有init--------init用于初始化连接池 close用于关闭前销毁
        DruidDataSource dds=new DruidDataSource();
        dds.setUrl(url);
        dds.setUsername(username);
        dds.setPassword(password);
        dds.setDriverClassName(driverclass);
        //以上只是配置了参数，并没有创建连接池  在这个类的init()中完成聊连接池创建
        log.info("配置了druid连接池大小"+cpuCount);
        dds.setInitialSize(cpuCount);
        dds.setMaxActive(cpuCount*2);
        return dds;
    }


    @Bean //IOC注解   托管第三方bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName(driverclass);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;

    }

    @Bean
    public DataSource dbcpDataSource(){
        BasicDataSource dataSource=new BasicDataSource();
        dataSource.setDriverClassName(driverclass);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        //TODO:更多参数
        return dataSource;
    }



}
