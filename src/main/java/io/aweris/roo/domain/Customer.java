package io.aweris.roo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document
@EqualsAndHashCode
@AllArgsConstructor
public class Customer {
    @Id
    private Long id;
    private BigDecimal payment;
}
