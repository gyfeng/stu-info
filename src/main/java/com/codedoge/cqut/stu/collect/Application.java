package com.codedoge.cqut.stu.collect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Code Doge(http://www.codedoge.com/)
 * @version 1.0
 * @since 1.0
 */
@Configuration
@ComponentScan
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"com.codedoge.cqut.stu.collect"})
@Controller
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
