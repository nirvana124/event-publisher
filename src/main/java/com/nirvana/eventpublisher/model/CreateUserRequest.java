package com.nirvana.eventpublisher.model;

import com.nirvana.eventpublisher.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String mobile;

    public User toUser() {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .mobile(mobile).build();
    }
}
