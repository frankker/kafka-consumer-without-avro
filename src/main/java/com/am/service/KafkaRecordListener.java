package com.am.service;

import static com.am.constants.KafkaContants.ASSET_TOPIC;

import com.am.dto.AssetDto;
import java.text.MessageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public class KafkaRecordListener {

  Logger logger = LoggerFactory.getLogger(KafkaRecordListener.class);

  @KafkaListener(topics = ASSET_TOPIC)
  void listenToAsset(
      @Payload String message,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
      @Header(KafkaHeaders.OFFSET) int offset) {
    logger.debug("===============Kafka Asset Message================");
    logger.info(
        MessageFormat.format(
            "Received asset message [{0}] from partition-{1} with offset-{2}",
            message, partition, offset));
  }
}
