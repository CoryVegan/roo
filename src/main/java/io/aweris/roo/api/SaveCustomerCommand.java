package io.aweris.roo.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveCustomerCommand {
    @Min(1)
    private long id;

    @Min(1)
    @NotNull
    private BigDecimal payment;
}
