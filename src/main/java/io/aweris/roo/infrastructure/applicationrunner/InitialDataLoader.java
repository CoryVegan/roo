package io.aweris.roo.infrastructure.applicationrunner;

import io.aweris.roo.api.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

import static io.aweris.roo.infrastructure.utlis.CustomerUtils.convertToCustomer;
import static java.util.Arrays.asList;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;

@Component
public class InitialDataLoader implements ApplicationRunner {
    private static final Logger LOG = LoggerFactory.getLogger(InitialDataLoader.class);

    private static String dataURL = "https://gist.githubusercontent.com/fjahr/b164a446db285e393d8e4b36d17f4143/raw/75108c09a72a001a985d27b968a0ac5a867e830b/smarthost_hotel_guests.json";

    private CustomerService service;
    private RestTemplate client;

    public InitialDataLoader(CustomerService service) {
        this.service = service;
        this.client = getRestTemplate();
    }

    @Override
    public void run(ApplicationArguments args) {
        LOG.info("Starting loading initial data ...");

        var data = Optional.ofNullable(this.client.getForObject(dataURL, BigDecimal[].class));

        if (data.isEmpty()) {
            LOG.error("Initial data is empty");
            return;
        }

        var payments = data.get();

        LOG.info("Initial data fetched. Total : {} records", payments.length);

        service.saveAll(convertToCustomer(payments)).doOnComplete(()-> LOG.info("Initial data loaded")).subscribe();
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
