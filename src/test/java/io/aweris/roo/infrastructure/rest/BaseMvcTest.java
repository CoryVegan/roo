package io.aweris.roo.infrastructure.rest;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@EnableSpringDataWebSupport
@RunWith(SpringRunner.class)
public abstract class BaseMvcTest {
    @Autowired
    WebTestClient webTestClient;
}
