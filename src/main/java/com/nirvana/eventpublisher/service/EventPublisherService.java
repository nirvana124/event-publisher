package com.nirvana.eventpublisher.service;

import reactor.core.publisher.Mono;

public interface EventPublisherService {
    void publish(String eventType, Object event);
}
