package io.aweris.roo.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OccupancyQuery {

    @Min(0)
    private long premium;

    @Min(0)
    private long economy;

    @Min(0)
    private BigDecimal limit = BigDecimal.valueOf(100);
}
