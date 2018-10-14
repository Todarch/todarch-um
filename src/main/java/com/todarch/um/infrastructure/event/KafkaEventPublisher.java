package com.todarch.um.infrastructure.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaEventPublisher implements EventPublisher {

  private final KafkaTemplate<String, BaseEvent> kafkaTemplate;

  @Override
  public void publish(BaseEvent eventToPublish) {
    kafkaTemplate.send(eventToPublish.getTopic(), eventToPublish);
    log.info("Published {} to {} with {}",
        eventToPublish.getEventName(),
        eventToPublish.getTopic(),
        eventToPublish.getEventId());
  }
}
