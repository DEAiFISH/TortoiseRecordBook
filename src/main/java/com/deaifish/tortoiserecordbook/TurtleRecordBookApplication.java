package com.deaifish.tortoiserecordbook;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.deaifish.tortoiserecordbook.mapper")
@EnableTransactionManagement    //开启数据库事务管理
@EnableEncryptableProperties    //开启jasypt加密
public class TurtleRecordBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(TurtleRecordBookApplication.class, args);
    }

}
