package eu.walletstreet.connector.coinmarketcap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/*
 * Bootstraps the Spring application
 */
@SpringBootApplication
@EnableScheduling
public class Main {

    public static void main(String... args) {
        SpringApplication app = new SpringApplication(new Object[] { Main.class });
        app.run(args);

    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}