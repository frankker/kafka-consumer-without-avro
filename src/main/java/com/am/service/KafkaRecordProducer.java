package com.am.service;

import static com.am.constants.KafkaContants.ASSET_TOPIC;

import com.am.dto.AssetDto;
import com.am.translator.ObjectTranslator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.MessageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaRecordProducer {
  private final KafkaTemplate<Long, String> template;
  private final Logger logger = LoggerFactory.getLogger(KafkaRecordProducer.class);

  public KafkaRecordProducer(
      KafkaTemplate<Long, String> template, ObjectTranslator objectTranslator) {
    this.template = template;
  }

  public void send(AssetDto assetDto) {
    String assetDtoString = "";
    try {
      assetDtoString = new ObjectMapper().writeValueAsString(assetDto);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    final long key = assetDto.getAssetId();
    this.template.send(ASSET_TOPIC, key, assetDtoString);
    logger.debug(
        MessageFormat.format("Produced event id {0} to kafka topic {1}", key, ASSET_TOPIC));
  }
}
