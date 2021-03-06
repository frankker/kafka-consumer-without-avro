package com.am.config;

import com.am.service.KafkaRecordListener;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
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

  @Value("${kafka.brokers.broker1.url}")
  private String kafkaBroker;

  @Value("${kafka.consumerGroup}")
  private String consumerGroup;

  @Value("${kafka.autoOffset}")
  private String autoOffset;

  @Value("${kafka.security.protocol}")
  private String securityProtocol;

  @Value("${kafka.security.mechanism}")
  private String saslMechanism;

  @Value("${kafka.security.username}")
  private String kafkaUsername;

  @Value("${kafka.security.password}")
  private String kafkaPassword;

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
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    //Security config
    final String saslJaasConfigString = MessageFormat.format("org.apache.kafka.common.security.scram.ScramLoginModule required username=\"{0}\" password=\"{1}\";", kafkaUsername, kafkaPassword);
    props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
    props.put(SaslConfigs.SASL_MECHANISM, saslMechanism);
    props.put(SaslConfigs.SASL_JAAS_CONFIG, saslJaasConfigString);
    return props;
  }

  @Bean
  public KafkaRecordListener listener() {
    return new KafkaRecordListener();
  }
}
