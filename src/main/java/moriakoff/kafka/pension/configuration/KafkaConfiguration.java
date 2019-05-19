package moriakoff.kafka.pension.configuration;

import moriakoff.kafka.pension.model.dto.PaymentDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@EnableKafka
public class KafkaConfiguration {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Bean
    /*@ConditionalOnMissingBean(ConsumerFactory.class)*/
    public ConsumerFactory <String, PaymentDto> paymentDtoConsumerFactory() {

        JsonDeserializer<PaymentDto> deserializer = new JsonDeserializer<>(PaymentDto.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map <String, Object> properties = new HashMap <>();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);

        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "pension");

        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory <>(properties, new StringDeserializer(),
                deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory <String, PaymentDto> paymentDtoKafkaContainerFactory() {

        ConcurrentKafkaListenerContainerFactory <String, PaymentDto> factory =
                new ConcurrentKafkaListenerContainerFactory <>();

        factory.setConsumerFactory(paymentDtoConsumerFactory());
        return factory;
    }
}
