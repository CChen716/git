package spring3.jdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper <T>{
    /**
     * 对第I行的ResultSet 转换成 T对象,   对这个具体是实现由用户完成
     *
     */
    public T mapper(ResultSet rs,int i) throws SQLException;
}
