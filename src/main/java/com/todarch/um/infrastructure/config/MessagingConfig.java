package com.todarch.um.infrastructure.config;

import com.todarch.um.infrastructure.channel.ProducerChannels;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(ProducerChannels.class)
public class MessagingConfig {
}
