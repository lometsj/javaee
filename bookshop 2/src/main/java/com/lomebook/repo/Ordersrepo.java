package com.lomebook.repo;

import com.lomebook.enti.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Ordersrepo extends JpaRepository<OrdersEntity,Long>{
}
