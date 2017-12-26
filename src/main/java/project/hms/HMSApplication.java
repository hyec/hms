package project.hms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 项目入口
 *
 * @see SpringApplication
 */
@SpringBootApplication
@EnableScheduling
public class HMSApplication {
    public static void main(String[] args) {
        SpringApplication.run(HMSApplication.class, args);
    }
}
