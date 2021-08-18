package com.am.controller;

import com.am.dto.AssetDto;
import com.am.service.KafkaRecordProducer;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class MainController {

  private final KafkaRecordProducer kafkaRecordProducer;

  public MainController(KafkaRecordProducer kafkaRecordProducer) {
    this.kafkaRecordProducer = kafkaRecordProducer;
  }

  @PutMapping("/create")
  public void createMessage(@RequestBody() AssetDto assetDto) throws InterruptedException {
    System.out.println("Sending alertDto " + assetDto);
    kafkaRecordProducer.send(assetDto);
    System.out.println("alertDto sent " + assetDto);
  }
}
