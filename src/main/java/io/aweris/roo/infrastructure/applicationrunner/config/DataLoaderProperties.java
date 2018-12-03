package io.aweris.roo.infrastructure.applicationrunner.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "dataloader.remote")
public class DataLoaderProperties {

    String url;
}
