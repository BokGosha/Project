package tbank.project.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DatabaseConfigProperties {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
}
