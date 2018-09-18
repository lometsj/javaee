package com.lomebook.repo;

import com.lomebook.enti.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Rolerepo extends JpaRepository<RoleEntity,Long>{
    RoleEntity findById(int Id);
}
