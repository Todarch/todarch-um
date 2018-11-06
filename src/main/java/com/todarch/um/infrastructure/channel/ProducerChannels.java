package com.todarch.um.infrastructure.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ProducerChannels {

  @Output("emailbinding")
  MessageChannel email();

}
