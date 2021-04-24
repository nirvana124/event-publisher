package com.nirvana.eventpublisher.service;

import com.nirvana.eventpublisher.entity.User;
import com.nirvana.eventpublisher.model.CreateUserRequest;
import com.nirvana.eventpublisher.model.UpdateMobileRequest;
import com.nirvana.eventpublisher.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.nirvana.eventpublisher.model.Events.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EventPublisherService eventPublisherService;

    @Override
    public Mono<String> create(CreateUserRequest createUserRequest) {
        return userRepository.save(createUserRequest.toUser())
                .doOnSuccess(user -> eventPublisherService.publish(USER_CREATED, user))
                .map(user -> user.getId());
    }

    @Override
    public Mono<User> updateMobile(UpdateMobileRequest updateMobileRequest) {
        var id = updateMobileRequest.getId();
        log.info("Updating mobile for id : {}", id);
        return userRepository.updateMobile(id, updateMobileRequest.getMobileNumber())
                .flatMap(integer -> {
                    if (integer > 0) {
                        return userRepository.findById(id);
                    }
                    throw new RuntimeException("No of rows updated " + integer);
                }).doOnSuccess(user -> eventPublisherService.publish(USER_UPDATED, user));
    }

    @Override
    public Mono<String> delete(String id) {
        return userRepository.deleteById(id)
                .map(unused -> id)
                .doOnSuccess(deletedUserId -> eventPublisherService.publish(USER_DELETED, deletedUserId));
    }

    @Override
    public Mono<User> getById(String id) {
        return userRepository.findById(id);
    }
}
