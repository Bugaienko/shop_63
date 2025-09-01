package ait.cohor63.shop.repository;

import ait.cohor63.shop.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Sergey Bugaenko
 * {@code @date} 01.09.2025
 */


public interface ProductRepository extends JpaRepository<Product, Long> {

    // Ни одного метода внутри не написано
}
