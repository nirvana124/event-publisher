package com.nirvana.eventpublisher.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nirvana.eventpublisher.model.EventDestination;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.nirvana.eventpublisher.model.EventDestination.SNS;
import static com.nirvana.eventpublisher.model.Events.USER_CREATED;
import static com.nirvana.eventpublisher.model.Events.USER_UPDATED;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventPublisherServiceImpl implements EventPublisherService {

    private final Map<String, EventPublisher> eventPublishers;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public void publish(String eventType, Object event) {
        log.info("Event of type {}", eventType);
        var messageId = publisher(eventType).publish(objectMapper.writeValueAsString(event));
        log.info("Message id {}", messageId);
    }

    private EventPublisher publisher(String eventType) {
        Map<String, EventDestination> eventDestinations = Map.of(
                USER_CREATED, SNS,
                USER_UPDATED, EventDestination.SQS);

        var destination = eventDestinations.getOrDefault(eventType, SNS);
        return eventPublishers.get(destination.getBeanName());
    }
}
