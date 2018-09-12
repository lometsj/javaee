package com.lomebook.repo;

import com.lomebook.enti.UsersEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Userrepo extends JpaRepository<UsersEntity,Long> {
    UsersEntity findByMail(String mail);
    UsersEntity findByLoginId(String loginId);
}
