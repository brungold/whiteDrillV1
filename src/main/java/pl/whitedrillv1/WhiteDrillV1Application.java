package pl.whitedrillv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.whitedrillv1.infrastructure.security.jwt.JwtConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {JwtConfigurationProperties.class})
public class WhiteDrillV1Application {

    public static void main(String[] args) {
        SpringApplication.run(WhiteDrillV1Application.class, args);
    }

}
