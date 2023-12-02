package com.deaifish.tortoiserecordbook;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
@EnableEncryptableProperties
@Component
class TurtlerecordbookApplicationTests {

    @Autowired
    StringEncryptor jasyptStringEncryptor;
    @Autowired
    DataSource dataSource;

    @Test
    public void testDataSource() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }

    @Test
    public void encrypt() {
        System.out.println("encrypt: " + jasyptStringEncryptor.encrypt("https://oss-accelerate.aliyuncs.com"));
    }

    @Test
    public void decrypt() {
        System.out.println("decrypt: " + jasyptStringEncryptor.decrypt("9TKOF846I3dO/qTyXjNUvXnqrH2lmq+xgxEj/MXXZDGSF/kPCLpxTb/WFalj8AF4"));
    }

}
