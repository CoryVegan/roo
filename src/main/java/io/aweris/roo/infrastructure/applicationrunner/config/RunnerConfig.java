package io.aweris.roo.infrastructure.applicationrunner.config;

import io.aweris.roo.api.CustomerService;
import io.aweris.roo.infrastructure.applicationrunner.InitialDataLoader;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DataLoaderProperties.class)
public class RunnerConfig {

    private DataLoaderProperties properties;

    public RunnerConfig(DataLoaderProperties properties) {
        this.properties = properties;
    }


    @Bean
    public InitialDataLoader initialDataLoader(CustomerService customerService) {
        return new InitialDataLoader(properties.url, customerService);
    }
}
