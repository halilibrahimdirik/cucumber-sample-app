package org.example.service;


import org.example.controller.OrderController;
import org.example.dao.OrderRepository;
import org.example.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootApplication
@ComponentScan({"org.example"})
@EntityScan("org.example.entity")
@EnableJpaRepositories("org.example.dao")
public class OrderService implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderController orderController;

    public static void main(String[] args) {
        SpringApplication.run(OrderService.class, args);
    }

    @Override
    public void run(String... args) {

        log.info("StartApplication...");

        repository.save(new Order("Truck"));
        repository.save(new Order("Car"));
        repository.save(new Order("Suv"));

        System.out.println("\nfindAll()");
        repository.findAll()
                .forEach(x -> System.out.println(x));

        System.out.println("\nfindById(1L)");
        repository.findById(1l)
                .ifPresent(x -> System.out.println(x));

        System.out.println("\nfindByName('Truck')");
        repository.findByName("Truck")
                .forEach(x -> System.out.println(x));

        ResponseEntity<List<Order>> allOrders = orderController.getAllOrders(null);

        System.out.println("\nfindAll()");
        List<Order> body = allOrders.getBody();
        body.forEach((temp) -> {
            System.out.println("Name is" + temp.getName());
        });

    }

}

