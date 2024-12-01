package com.coinmasters.dao;

import com.coinmasters.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> getGroupByGroupId(Long groupId);
    Optional<Group> getGroupByJoinCode(String joinCode);
}
