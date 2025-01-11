package com.john_henry.User.infrastructure.adapters.output.persistence.repository;

import com.john_henry.User.infrastructure.adapters.output.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity,Integer> {

}
