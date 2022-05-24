package org.example.dao;

import org.example.entity.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByName(String name);

    List<Order> findByNameContaining(String name);

}
