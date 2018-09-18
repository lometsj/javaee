package com.lomebook.repo;

import com.lomebook.enti.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Cartrepo extends JpaRepository<CartEntity,Long>{
    List <CartEntity> findAllByUserId(int UserId);
    CartEntity findByUserIdAndBookId(int UserId,int BookId);
    CartEntity findById(int Id);
}
