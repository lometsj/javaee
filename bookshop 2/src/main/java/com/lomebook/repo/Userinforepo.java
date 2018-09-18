package com.lomebook.repo;

import com.lomebook.enti.UserInfoEntity;
import com.lomebook.enti.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Userinforepo extends JpaRepository<UserInfoEntity,Long>{
    UserInfoEntity findByUserName(String UserName);
    UserInfoEntity findById(int Id);
}
