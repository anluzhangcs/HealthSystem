package org.graduate;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Zhanghao
 * @date: 2023/4/3-15:25
 * @Description
 */
@Slf4j
@SpringBootApplication
@MapperScan("org.graduate.mapper")
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
