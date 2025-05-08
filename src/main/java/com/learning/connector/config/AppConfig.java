package com.learning.connector.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author vickyaa
 * @date 5/5/25
 * @file AppConfig
 */

@Configuration
@ComponentScan("com.learning.connector")
@EnableJpaRepositories("com.learning.connector.repository")
@EnableMongoRepositories("com.learning.connector.repository")
public class AppConfig {

}
