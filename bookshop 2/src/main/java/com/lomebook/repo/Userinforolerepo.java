package com.lomebook.repo;

import com.lomebook.enti.UserInfoRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Userinforolerepo extends JpaRepository<UserInfoRoleEntity,Long>{
    List<UserInfoRoleEntity> findAllByUserInfoId(int UserInfoId);
}
