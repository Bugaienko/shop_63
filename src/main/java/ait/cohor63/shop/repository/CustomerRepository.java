package ait.cohor63.shop.repository;

import ait.cohor63.shop.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Sergey Bugaenko
 * {@code @date} 11.09.2025
 */

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByName(String name);
}
