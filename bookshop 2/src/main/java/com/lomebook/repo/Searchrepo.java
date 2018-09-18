package com.lomebook.repo;

import com.lomebook.enti.SearchDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Searchrepo extends JpaRepository<SearchDetailsEntity,Long> {

}
