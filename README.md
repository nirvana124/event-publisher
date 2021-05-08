# event-publisher
As we are developing event driven microservices. We are publishing events to different destinations 
like `SNS`, `SQS`, `Kafka`, `Kinesis` or any other in house or cloud service.

This repository has a simple example of publishing event to SQS or SNS 
when there is any update in user details based on event type. 
In case there is no explicit event type to destination mapping event will be published to `SNS`.

For simplicity kept only 3 events getting published to SQS/SNS.
1. `USER_CREATED` to `SNS`
2. `USER_UPDATED` to `SQS`
3. `USER_DELETED` to `SNS` as it is default destination.

As usual spring boot application, we have a controller,
service where handling request and response transformation 
or any business logic and publishing the event and repository 
for DB operations.

You can send a `POST` request to create a new user and publish `USER_CREATED` event.
As you can see in below code snipped once we are saving the user to database, we are calling `publish` method of publisher service.

```
public Mono<String> create(CreateUserRequest createUserRequest) {
    return userRepository.save(createUserRequest.toUser())
            .doOnSuccess(user -> eventPublisherService.publish(USER_CREATED, user))
            .map(user -> user.getId());
}
```

Based on the event publisher service will get the `EventPublisher` object based on event type.

```
@Service
@RequiredArgsConstructor
@Slf4j
public class EventPublisherServiceImpl implements EventPublisherService {
    private final Map<String, EventPublisher> eventPublishers;
    private final ObjectMapper objectMapper;
}
```

This class is most interesting part of the complete application.
As you can see in the above code `Map<String, EventPublisher> eventPublishers` is injected from constructor,
but we have not created any such bean in our code.
So the question is how is it going to work if we don't have any bean.
Answer is simple `Spring application context` is giving it to us out of the box.
We just need to give a name to our `EventPublisher` beans.

```
@Service("snsPublisher")
@Slf4j
public class SnsEventPublisher implements EventPublisher

@Service("sqsPublisher")
@Slf4j
public class SqsEventPublisher implements EventPublisher
```

Let's see how we are getting publisher from event type.
I have hardcoded the `eventDestinations`, but these can be fetched from your DB or
can be loaded during startup from configuration properties. I am using SNS as default publisher.

```
private EventPublisher publisher(String eventType) {
    Map<String, EventDestination> eventDestinations = Map.of(
            USER_CREATED, SNS,
            USER_UPDATED, EventDestination.SQS);

    var destination = eventDestinations.getOrDefault(eventType, SNS);
    return eventPublishers.get(destination.getBeanName());
}
```

In our `EventDestination` enum we have added the bean name of the publisher for that destination.
So from bean name we are getting from our enum we are getting the actual bean from the `eventPublishers` map.

```
@AllArgsConstructor
@Getter
public enum EventDestination {
    SQS("sqsPublisher"), SNS("snsPublisher");
    private final String beanName;
}
```

This is kind of a factory which is giving you publisher and publishing the message to required publisher.