package com.nirvana.eventpublisher.service;

import java.util.concurrent.ExecutionException;

public interface EventPublisher {
    String publish(Object event);
}
