package com.learning.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan({"com.learning"})
public class JdbcConfiguration {

  @Value("{db.driver}")
  private final String DRIVER;

  @Value("{db.url}")
  private final String URL;

  @Value("{db.username}")
  private final String USERNAME;

  @Value("{db.password}")
  private final String PASSWORD;

  public JdbcConfiguration(String driver, String url, String username, String password) {
    DRIVER = driver;
    URL = url;
    USERNAME = username;
    PASSWORD = password;
  }

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(DRIVER);
    dataSource.setUrl(URL);
    dataSource.setUsername(USERNAME);
    dataSource.setPassword(PASSWORD);
    return dataSource;
  }

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource());
  }

  @Bean
  public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
    return new NamedParameterJdbcTemplate(dataSource());
  }
}
