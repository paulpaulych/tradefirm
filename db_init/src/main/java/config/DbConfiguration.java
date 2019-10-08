package config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Configuration
@ConfigurationProperties(prefix = "db")
public class DbConfiguration {

    @Value("url")
    private String url;

    @Value("username")
    private String username;

    @Value("password")
    private String password;
}
