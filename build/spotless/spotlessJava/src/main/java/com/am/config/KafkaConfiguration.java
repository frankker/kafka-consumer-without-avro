package com.am.config;

import com.am.service.KafkaRecordListener;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
@EnableKafka
public class KafkaConfiguration {

  @Value("${kafka.broker1.url}")
  private String kafkaBroker;

  @Value("${kafka.consumerGroup}")
  private String consumerGroup;

  @Value("${kafka.autoOffset}")
  private String autoOffset;

  @Value("${kafka.schemaRegistry}")
  private String schemaRegistry;

  @Bean
  ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory(
      ConsumerFactory<Integer, String> consumerFactory) {
    ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);
    return factory;
  }

  @Bean
  public ConsumerFactory<Integer, String> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerProps());
  }

  private Map<String, Object> consumerProps() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBroker);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffset);
    // Avro properties
    props.put(
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        io.confluent.kafka.serializers.KafkaAvroDeserializer.class);
    props.put(
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        io.confluent.kafka.serializers.KafkaAvroDeserializer.class);
    props.put("schema.registry.url", schemaRegistry);
    props.put("specific.avro.reader", true);
    return props;
  }

  @Bean
  public KafkaRecordListener listener() {
    return new KafkaRecordListener();
  }
}
