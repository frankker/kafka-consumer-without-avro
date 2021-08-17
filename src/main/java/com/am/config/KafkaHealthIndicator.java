package com.am.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterOptions;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class KafkaHealthIndicator implements HealthIndicator {

  @Value("${kafka.brokers.broker1.url}")
  private String kafkaBroker;

  @Override
  public Health health() {
    Health.Builder builder = Health.up();
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBroker);
    final DescribeClusterOptions options = new DescribeClusterOptions().timeoutMs(1000);
    final AdminClient kafkaAdminClient = AdminClient.create(props);
    final DescribeClusterResult describeClusterResult = kafkaAdminClient.describeCluster(options);
    try {
      return builder
          .up()
          .withDetail("clusterId", describeClusterResult.clusterId().get())
          .withDetail("nodeCount", describeClusterResult.nodes().get().size())
          .build();
    } catch (Exception ex) {
      return Health.down().build();
    }
  }
}
