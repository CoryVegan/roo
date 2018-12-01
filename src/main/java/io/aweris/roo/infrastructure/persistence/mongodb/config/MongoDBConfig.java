package io.aweris.roo.infrastructure.persistence.mongodb.config;

import io.aweris.roo.infrastructure.persistence.mongodb.converter.BigDecimalReadConverter;
import io.aweris.roo.infrastructure.persistence.mongodb.converter.BigDecimalWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoDBConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new BigDecimalReadConverter());
        converters.add(new BigDecimalWriteConverter());
        return new MongoCustomConversions(converters);
    }

    @Bean
    public LoggingEventListener mongoEventListener() {
        return new LoggingEventListener();
    }
}
