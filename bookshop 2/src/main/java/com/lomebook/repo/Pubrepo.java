package com.lomebook.repo;

import com.lomebook.enti.PublishersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Pubrepo extends JpaRepository<PublishersEntity,Long>{
    PublishersEntity findById(int Id);
}
