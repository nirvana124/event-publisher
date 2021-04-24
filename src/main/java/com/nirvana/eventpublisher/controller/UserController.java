package com.nirvana.eventpublisher.controller;

import com.nirvana.eventpublisher.entity.User;
import com.nirvana.eventpublisher.model.CreateUserRequest;
import com.nirvana.eventpublisher.model.UpdateMobileRequest;
import com.nirvana.eventpublisher.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public Mono<String> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return userService.create(createUserRequest);
    }

    @PatchMapping
    public Mono<User> updateUserMobile(@RequestBody UpdateMobileRequest updateMobileRequest) {
        return userService.updateMobile(updateMobileRequest);
    }

    @DeleteMapping("/{id}")
    public Mono<String> deleteUser(@PathVariable String id) {
        return userService.delete(id);
    }

    @GetMapping("/{id}")
    public Mono<User> getUser(@PathVariable String id) {
        return userService.getById(id);
    }
}
