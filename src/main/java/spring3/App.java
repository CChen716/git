package spring3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import spring3.user.BankAccount;
import spring3.user.BankAccountDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
@Service
public class App {
    public static void main(String[] args) throws SQLException {
        ApplicationContext ac=new AnnotationConfigApplicationContext(Config.class);
        DataSource ds= (DataSource) ac.getBean("myDataSource");

//        Connection con=ds.getConnection();
//        System.out.println(con);

        BankAccountDao bad= (BankAccountDao) ac.getBean("bankAccountDao");
        List<BankAccount> list=bad.findAll();
        for (BankAccount ba : list) {
            System.out.println(ba);
        }

    }
}
