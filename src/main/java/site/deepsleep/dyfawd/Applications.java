package site.deepsleep.dyfawd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Applications {
    public static void main(String[] args)  {
        SpringApplication.run(Applications.class, args);
    }
}
