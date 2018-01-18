package eu.walletstreet.connector.twitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
 * Bootstraps the Spring application
 */
@SpringBootApplication
@EnableScheduling
public class Main {

    public static void main(String... args) {
        SpringApplication app = new SpringApplication(new Object[] { Main.class });
        app.addListeners(new ClientStarter());
        app.run(args);

    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}