package io.aweris.roo.infrastructure.persistence.mongodb.converter;

import org.bson.types.Decimal128;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.math.BigDecimal;

@ReadingConverter
public class BigDecimalReadConverter implements Converter<Decimal128, BigDecimal> {
    @Override
    public BigDecimal convert(Decimal128 decimal128) {
        return decimal128.bigDecimalValue();
    }
}
