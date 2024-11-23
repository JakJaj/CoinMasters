package com.coinmasters.dao;

import com.coinmasters.entity.UserGroup.UserGroup;
import com.coinmasters.entity.UserGroup.UserGroupId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UserGroup, UserGroupId> {
}
