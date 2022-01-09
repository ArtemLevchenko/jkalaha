package com.artbender.service;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration. To scan spring configuration in service packages
 *
 * @author Artsiom Leuchanka
 */
@ComponentScan("com.artbender.service")
@Configuration
@EnableJpaRepositories
@EntityScan("com.artbender.core")
public class ServiceConfig {
}
