package com.am.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@EnableKafka
public class KafkaConfiguration {
  @Bean
  public KafkaTemplate<Long, String> kafkaTemplate(ProducerFactory<Long, String> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }
}
