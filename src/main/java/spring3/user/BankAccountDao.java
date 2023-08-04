package spring3.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import spring3.jdbcTemplate.JdbcTemplate;
import spring3.jdbcTemplate.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BankAccountDao extends JdbcTemplate {
    @Autowired  //按类型注入
    public BankAccountDao(DataSource dataSource){
        super(dataSource);
    }

    public List<BankAccount> findAll(){
        return super.executeQuery("select * from bank where id=?", new RowMapper<BankAccount>() {

            @Override
            public BankAccount mapper(ResultSet rs, int i) throws SQLException {
                BankAccount ba=new BankAccount();
                ba.setId(rs.getInt(1));
                ba.setBalance(rs.getDouble(2));
                return ba;
            }
        },1);
    }
}
