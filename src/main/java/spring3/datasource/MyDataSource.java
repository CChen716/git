package spring3.datasource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

@Component
public class MyDataSource implements DataSource {
    private ConcurrentLinkedQueue<MyConnection> pool;

    //配置后注入   结合属性文件完成注入操作
    //3.从属性文件中取
    @Value("${coreSize}")
    private int coreSize;
    @Value("${mysql.username}")
    private String username;
    @Value("${mysql.password}")
    private String password;
    @Value("${mysql.url}")
    private String url;

    public MyDataSource(){
        System.out.println("MyDataSource的构造方法");
    }

    @PostConstruct //此方法在 构造方法被执行后  执行  即初始化连接池
    public void init() throws SQLException {
        System.out.println("MyDataSource的init()");
        pool=new ConcurrentLinkedQueue<>();
        for (int i=0;i<coreSize;i++){
            MyConnection mc=new MyConnection();
            mc.statue=false;
            mc.con= DriverManager.getConnection(url,username,password);
            pool.add(mc);

        }
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    class MyConnection{
        Connection con;
        boolean statue;
    }

    public Connection getConnection(){
        do {
            MyConnection mc=this.pool.poll();
            if (mc==null){
                return null;
            }
            if (!mc.statue){
                return mc.con;
            }
        }while (true);
    }

    public void returnConnection(Connection con){
        MyConnection mc=new MyConnection();
        mc.statue=false;
        mc.con=con;
        this.pool.add(mc);
    }

    public Connection getConnection(String username,String password){
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

}
