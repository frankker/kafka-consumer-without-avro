package com.am.service;

import example.kafka.dto.AssetDto;
import java.text.MessageFormat;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public class KafkaRecordListener {

  @KafkaListener(topics = "asset05")
  void listenToAsset(
      @Payload AssetDto message,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
      @Header(KafkaHeaders.OFFSET) int offset) {
    System.out.println("===============Kafka Asset Message================");
    System.out.println(
        MessageFormat.format(
            "Received asset message [{0}] from partition-{1} with offset-{2}",
            message, partition, offset));
  }
}
