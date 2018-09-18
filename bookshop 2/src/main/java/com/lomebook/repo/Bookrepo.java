package com.lomebook.repo;


import java.util.List;

import javax.transaction.Transactional;

import com.lomebook.BookshopApplication;
import com.lomebook.enti.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface Bookrepo extends JpaRepository<BooksEntity,Long> {

    BooksEntity findById (int Id);
    List<BooksEntity> findAllByIsbnLike(String Isbn);
    List<BooksEntity> findAllByTitleLike(String Title);


}
