package io.aweris.roo.infrastructure.applicationrunner;

import io.aweris.roo.api.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

import static io.aweris.roo.infrastructure.utlis.CustomerUtils.convertToCustomer;

public class InitialDataLoader implements ApplicationRunner {
    private static final Logger LOG = LoggerFactory.getLogger(InitialDataLoader.class);

    private String dataURL;
    private CustomerService service;
    private RestTemplate client;

    public InitialDataLoader(String dataURL, CustomerService service, RestTemplate client) {
        this.dataURL = dataURL;
        this.service = service;
        this.client = client;
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

        service.saveAll(convertToCustomer(payments)).doOnComplete(() -> LOG.info("Initial data loaded")).subscribe();
    }
}
