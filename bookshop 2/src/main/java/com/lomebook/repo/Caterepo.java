package com.lomebook.repo;

import com.lomebook.enti.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Caterepo extends JpaRepository<CategoriesEntity,Long>{
    CategoriesEntity findById(int Id);
}
