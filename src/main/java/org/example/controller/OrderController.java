package org.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.dao.OrderRepository;
import org.example.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders(
            @RequestParam(required = false)
            String orderName) {
        try {
            List<Order> orders = new ArrayList<>();
            if (orderName == null) {
                orderRepository.findAll()
                        .forEach(orders::add);
            } else {
                orderRepository.findByNameContaining(orderName)
                        .forEach(orders::add);
            }
            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(
            @PathVariable("id")
            long id) {
        Optional<Order> OrderData = orderRepository.findById(id);

        if (OrderData.isPresent()) {
            return new ResponseEntity<>(OrderData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(
            @RequestBody
            Order Order) {
        try {
            Order _Order = orderRepository.save(new Order(Order.getName()));
            return new ResponseEntity<>(_Order, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable("id")
            long id,
            @RequestBody
            Order Order) {
        Optional<Order> OrderData = orderRepository.findById(id);

        if (OrderData.isPresent()) {
            Order _Order = OrderData.get();
            _Order.setName(Order.getName());
            return new ResponseEntity<>(orderRepository.save(_Order), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(
            @PathVariable("id")
            long id) {
        try {
            orderRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/orders")
    public ResponseEntity<HttpStatus> deleteAllOrders() {
        try {
            orderRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/orders/published")
    public ResponseEntity<List<Order>> findByName(
            @PathVariable("name")
            String name) {
        try {
            List<Order> Orders = orderRepository.findByName(name);

            if (Orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(Orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
