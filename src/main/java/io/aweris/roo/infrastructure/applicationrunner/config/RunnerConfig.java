package io.aweris.roo.infrastructure.applicationrunner.config;

import io.aweris.roo.api.CustomerService;
import io.aweris.roo.infrastructure.applicationrunner.InitialDataLoader;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import static java.util.Arrays.asList;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;

@Configuration
@EnableConfigurationProperties(DataLoaderProperties.class)
public class RunnerConfig {

    private DataLoaderProperties properties;

    public RunnerConfig(DataLoaderProperties properties) {
        this.properties = properties;
    }

    @Bean
    public InitialDataLoader initialDataLoader(CustomerService customerService) {
        return new InitialDataLoader(properties.url, customerService, getRestTemplate());
    }

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // our data url return response type as text but our data is json
        // that's why we need to add TEXT_PLAIN to supported media types
        var converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(asList(TEXT_PLAIN, APPLICATION_JSON));
        restTemplate.getMessageConverters().add(0, converter);

        return restTemplate;
    }
}
