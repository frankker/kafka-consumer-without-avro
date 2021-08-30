package com.am.service;

import static com.am.constants.KafkaContants.ASSET_TOPIC;

import com.am.constants.AssetStatusEnum;
import com.am.dao.AssetRepository;
import com.am.dao.entity.AssetEntity;
import com.am.dto.AssetDto;
import com.am.translator.ObjectTranslator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.MessageFormat;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
public class KafkaRecordListener {
  @Autowired private AssetRepository assetRepository;
  @Autowired private ObjectTranslator objectTranslator;

  Logger logger = LoggerFactory.getLogger(KafkaRecordListener.class);

  @RetryableTopic(
      attempts = "3",
      backoff = @Backoff(delay = 1000, multiplier = 2.0),
      autoCreateTopics = "true",
      topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE)
  @KafkaListener(topics = ASSET_TOPIC)
  void listenToAsset(
      @Payload String message,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
      @Header(KafkaHeaders.OFFSET) int offset,
      Acknowledgment acknowledgment) throws Exception {
    logger.debug("===============Kafka Asset Message================");
    logger.info(
        MessageFormat.format(
            "Received asset message [{0}] from partition-{1} with offset-{2}",
            message, partition, offset));

    AssetDto assetDto = new AssetDto();
    try {
      assetDto = new ObjectMapper().readValue(message, AssetDto.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    AssetEntity assetEntity = buildAssetEntity(assetDto);


    //    acknowledgment.acknowledge();

    if (assetDto.getAssetId() % 2 == 0) {
      acknowledgment.acknowledge();
      logger.info("Error occurred for " + assetDto.getAssetId());
      throw new Exception();
    } else {
      logger.info("Produced and consumed::" + message);
      acknowledgment.acknowledge();
      assetRepository.save(assetEntity);
    }

  }

  @DltHandler
  public void dlt(String in, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Acknowledgment acknowledgment) {
    logger.info("Adding to DLT for " + topic + " - " + in);
    acknowledgment.acknowledge();
  }

  private AssetEntity buildAssetEntity(AssetDto assetDto) {
    AssetEntity assetEntity = objectTranslator.translate(assetDto, AssetEntity.class);
    if (assetDto.getAssetStatusResponse() != null
        && EnumUtils.isValidEnum(
            AssetStatusEnum.class, assetDto.getAssetStatusResponse().getCode())) {
      assetEntity.setAssetStatus(
          AssetStatusEnum.valueOf(assetDto.getAssetStatusResponse().getCode()));
    }

    if (assetDto.getAssetType() != null) {
      assetEntity.setAssetTypeCode(assetDto.getAssetType().getCode());
    }

    if (assetDto.getScanCodeTypeDetails() != null) {
      assetEntity.setScanCodeType(assetDto.getScanCodeTypeDetails().getCode());
    }

    if (assetDto.getOwnerShipTypeResponse() != null) {
      assetEntity.setOwnerShipTypeCode(assetDto.getOwnerShipTypeResponse().getCode());
    }

    return assetEntity;
  }
}
