package ait.cohor63.shop.controller;

import ait.cohor63.shop.model.dto.CustomerDTO;
import ait.cohor63.shop.service.interfaces.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Sergey Bugaenko
 * {@code @date} 15.09.2025
 */

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.saveCustomer(customerDTO);
    }

    @GetMapping
    public List<CustomerDTO> getAllActiveCustomers() {
        return customerService.getAllActiveCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id,@RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(id, customerDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }

    @DeleteMapping("/name/{name}")
    public void deleteCustomerByName(@PathVariable String name) {
        customerService.deleteCustomerByName(name);
    }

    @PutMapping("/restore/{id}")
    public CustomerDTO restoreCustomerById(@PathVariable Long id) {
        return customerService.restoreCustomerById(id);
    }

    @GetMapping("/count")
    public long getActiveCustomerCount() {
        return customerService.getActiveCustomerCount();
    }

    @GetMapping("/{id}/cart/total-price")
    public BigDecimal getCartTotalPrice(@PathVariable("id") Long customerId) {
        return customerService.getCartTotalPrice(customerId);
    }

    @GetMapping("/{id}/cart/average-price")
    public BigDecimal getCartAveragePrice(@PathVariable("id") Long customerId) {
        return customerService.getCartAveragePrice(customerId);
    }

    @PostMapping("/{customerId}/cart/{productId}")
    public void addProductToCart(@PathVariable Long customerId, @PathVariable Long productId) {
        customerService.addProductToCart(customerId, productId);
    }

    @DeleteMapping("/{customerId}/cart/{productId}")
    public void removeProductFromCart(@PathVariable Long customerId,@PathVariable Long productId) {
        customerService.removeProductFromCart(customerId, productId);
    }

    @GetMapping("/{id}/cart/clear")
    public void clearCustomerCart(@PathVariable("id") Long customerId) {
        customerService.clearCustomerCart(customerId);
    }
}
