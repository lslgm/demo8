package com.example.demo.DBConnection;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
@Log4j2
public class DataSourceTest {
    @Autowired
    private DataSource dataSource;
    @Test
    public void testConnection() throws SQLException{
        @Cleanup
        Connection con = dataSource.getConnection();
        Assertions.assertNotNull(con); //데이터베이스 값이 null아니면 데이터베이스연결완료
        log.info("데이터베이스연결 성공");
    }
}
