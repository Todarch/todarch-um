package com.todarch.um.infrastructure.event;

import java.time.Instant;

public interface BaseEvent {
  String getEventId();

  String getTopic();

  default String getEventName() {
    return this.getClass().getSimpleName();
  }

  default long occuredAt() {
    return Instant.now().toEpochMilli();
  }

  default String triggeredBy() {
    return "some universal id";
  }
}
