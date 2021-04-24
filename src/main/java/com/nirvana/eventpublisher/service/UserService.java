package com.nirvana.eventpublisher.service;

import com.nirvana.eventpublisher.entity.User;
import com.nirvana.eventpublisher.model.CreateUserRequest;
import com.nirvana.eventpublisher.model.UpdateMobileRequest;
import com.nirvana.eventpublisher.model.UpdateUserRequest;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<String> create(CreateUserRequest createUserRequest);
    Mono<String> delete(String id);
    Mono<User> getById(String id);
    Mono<User> updateMobile(UpdateMobileRequest updateMobileRequest);
}
