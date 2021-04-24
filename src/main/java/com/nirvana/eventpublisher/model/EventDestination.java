package com.nirvana.eventpublisher.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EventDestination {
    SQS("sqsPublisher"), SNS("snsPublisher");
//    EVENT_BRIDGE("eventBridgePublisher"), KINESIS("kinesisPublisher"), MSK("kafkaPublisher");
    private String beanName;
}
