package com.am.service;

import com.am.dto.AssetDto;
import com.am.translator.ObjectTranslator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaRecordProducer {
  private ObjectTranslator objectTranslator;
  private KafkaTemplate<Long, String> template;

  public KafkaRecordProducer(
      KafkaTemplate<Long, String> template, ObjectTranslator objectTranslator) {
    this.template = template;
    this.objectTranslator = objectTranslator;
  }

  public void send(AssetDto assetDto) {
    String assetDtoString = "";
    try {
      assetDtoString = new ObjectMapper().writeValueAsString(assetDto);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    String topic = "asset";
    long key = assetDto.getAssetId();
    this.template.send(topic, key, assetDtoString);
  }
}
