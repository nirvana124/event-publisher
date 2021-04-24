package com.nirvana.eventpublisher.repository;

import com.nirvana.eventpublisher.entity.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, String> {

    @Modifying
    @Query("UPDATE user SET mobile = :mobile where id = :id")
    Mono<Integer> updateMobile(@Param(value = "id") String id, @Param(value = "mobile") String mobile);
}
