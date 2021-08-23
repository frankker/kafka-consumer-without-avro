package com.am.config;

import com.am.service.KafkaRecordListener;
import com.am.service.KafkaRecordProducer;
import com.am.translator.ObjectTranslator;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
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
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

@Configuration
@EnableKafka
public class KafkaConfiguration {

  @Value("${kafka.brokers.urls}")
  private String kafkaBroker;

  @Value("${kafka.consumerGroup}")
  private String consumerGroup;

  @Value("${kafka.consumerGroupInstanceID}")
  private String consumerGroupInstanceID;

  @Value("${kafka.consumerHeartBeatSessionTimeout}")
  private String consumerHeartBeatSessionTimeout;

  @Value("${kafka.maxPollInterval}")
  private int maxPollInterval;

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
  ConcurrentKafkaListenerContainerFactory<Long, String> kafkaListenerContainerFactory(
      ConsumerFactory<Long, String> consumerFactory) {
    ConcurrentKafkaListenerContainerFactory<Long, String> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
    return factory;
  }

  @Bean
  public ConsumerFactory<Long, String> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerProps());
  }

  private Map<String, Object> consumerProps() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBroker);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffset);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, consumerGroupInstanceID);
    //auto commit settings
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollInterval);
    //static membership
    props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, consumerGroupInstanceID);
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, consumerHeartBeatSessionTimeout);
    // Security config
    final String saslJaasConfigString =
        MessageFormat.format(
            "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"{0}\" password=\"{1}\";",
            kafkaUsername, kafkaPassword);
    //    props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
    //    props.put(SaslConfigs.SASL_MECHANISM, saslMechanism);
    //    props.put(SaslConfigs.SASL_JAAS_CONFIG, saslJaasConfigString);
    return props;
  }

  @Bean
  public KafkaRecordListener listener() {
    return new KafkaRecordListener();
  }

  // Producer

  @Bean
  public KafkaRecordProducer sender(
      KafkaTemplate<Long, String> template, ObjectTranslator objectTranslator) {
    return new KafkaRecordProducer(template, objectTranslator);
  }

  @Bean
  public ProducerFactory<Long, String> producerFactory() {
    return new DefaultKafkaProducerFactory<>(senderProps());
  }

  private Map<String, Object> senderProps() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    // ...
    return props;
  }

  @Bean
  public KafkaTemplate<Long, String> kafkaTemplate(ProducerFactory<Long, String> producerFactory) {
    return new KafkaTemplate<Long, String>(producerFactory);
  }
}
