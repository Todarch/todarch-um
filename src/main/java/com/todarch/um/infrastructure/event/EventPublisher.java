package com.todarch.um.infrastructure.event;

public interface EventPublisher {

  void publish(BaseEvent eventToPublish);

}
