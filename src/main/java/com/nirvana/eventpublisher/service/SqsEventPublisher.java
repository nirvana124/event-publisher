package com.nirvana.eventpublisher.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service("sqsPublisher")
@Slf4j
public class SqsEventPublisher implements EventPublisher {
    @Override
    public String publish(Object event) {
        log.info("Using SQS Publisher");
        return SqsClient.create().sendMessage(SendMessageRequest.builder()
//                .queueUrl("https://sqs.ap-south-1.amazonaws.com/XXXXXX/Queue")
                .messageBody(event.toString()).build()).messageId();
    }
}
