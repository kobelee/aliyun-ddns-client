package cn.kobelee.ipresolve.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Description:
 *
 * @author Kobe Lee
 * @email kobe663@gmail.com
 * @date 11/18/2021
 */
@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class);
        System.out.println("started!!!");
    }
}
