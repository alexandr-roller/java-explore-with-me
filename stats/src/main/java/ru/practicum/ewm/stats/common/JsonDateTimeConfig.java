package ru.practicum.ewm.stats.common;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ru.practicum.ewm.stats.common.CommonFormats.DATE_TIME_FORMATTER;

@Configuration
public class JsonDateTimeConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonDateTimeCustomizer() {
        return builder -> {
            builder.serializers(new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
            builder.deserializers(new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));
        };
    }
}
