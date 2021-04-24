package com.nirvana.eventpublisher.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Service("snsPublisher")
@Slf4j
public class SnsEventPublisher implements EventPublisher {
    @Override
    public String publish(Object event) {
        log.info("Using SNS Publisher");
        return SnsClient.create().publish(PublishRequest.builder()
//                .topicArn("arn:aws:sns:ap-south-1:XXXXXXX:topic")
                .message(event.toString()).build()).messageId();
    }
}
