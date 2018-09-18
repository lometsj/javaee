package com.lomebook.repo;

import com.lomebook.enti.OrderBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Orderbookrepo extends JpaRepository<OrderBookEntity,Long>{

}
