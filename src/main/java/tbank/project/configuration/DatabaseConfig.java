package tbank.project.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {
    private final DatabaseConfigProperties databaseConfigProperties;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(databaseConfigProperties.getDriverClassName());
        dataSource.setUrl(databaseConfigProperties.getUrl());
        dataSource.setUsername(databaseConfigProperties.getUsername());
        dataSource.setPassword(databaseConfigProperties.getPassword());

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
