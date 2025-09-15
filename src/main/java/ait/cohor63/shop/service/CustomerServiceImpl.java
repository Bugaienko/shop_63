package ait.cohor63.shop.service;

import ait.cohor63.shop.model.dto.CustomerDTO;
import ait.cohor63.shop.model.entity.Cart;
import ait.cohor63.shop.model.entity.Customer;
import ait.cohor63.shop.model.entity.Product;
import ait.cohor63.shop.repository.CustomerRepository;
import ait.cohor63.shop.repository.ProductRepository;
import ait.cohor63.shop.service.interfaces.CustomerService;
import ait.cohor63.shop.service.mapping.CustomerMapperService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author Sergey Bugaenko
 * {@code @date} 15.09.2025
 */

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapperService customerMapper;

    private final ProductRepository productRepository;

    public CustomerServiceImpl(CustomerRepository repository, CustomerMapperService customerMapper, ProductRepository productRepository) {
        this.repository = repository;
        this.customerMapper = customerMapper;
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.mapDtoToEntity(customerDTO);

        Cart cart = new Cart();
        cart.setCustomer(customer);
        customer.setCart(cart);



        return customerMapper.mapEntityToDto(repository.save(customer));

    }

    @Override
    public List<CustomerDTO> getAllActiveCustomers() {
        return repository.findAll().stream()
                .filter(Customer::isActive)
                .map(customerMapper::mapEntityToDto)
                .toList();
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = repository.findById(id).orElse(null);

        if (customer != null && customer.isActive()) {
            return customerMapper.mapEntityToDto(customer);
        }

        throw new RuntimeException("Customer with id " + id + " not found");

    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        return null;
    }

    @Transactional
    @Override
    public void deleteCustomerById(Long id) {
        Optional<Customer> customerOptional = repository.findById(id);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setActive(false);
        } else throw new RuntimeException("Customer with id " + id + " not found");

    }

    @Transactional
    @Override
    public void deleteCustomerByName(String name) {
        Optional<Customer> customerOptional = repository.findByName(name);

        Customer customer = customerOptional.orElseThrow(() -> new RuntimeException("Customer with name " + name + " not found"));
        customer.setActive(false);
    }

    @Override
    public CustomerDTO restoreCustomerById(Long id) {
        Optional<Customer> optionalCustomer = repository.findById(id);

        Customer customer = optionalCustomer.orElseThrow(() -> new RuntimeException("Customer with id " + id + " not found"));
        customer.setActive(true);
        return customerMapper.mapEntityToDto(customer);
    }

    @Override
    public long getActiveCustomerCount() {
        return repository.findAll().stream()
                .filter(Customer::isActive)
                .count();
    }

    @Override
    public BigDecimal getCartTotalPrice(Long customerId) {
        Optional<Customer> customerOptional = repository.findById(customerId);

        Customer customer = customerOptional.orElseThrow(() -> new RuntimeException("Customer with id " + customerId + " not found"));
        Cart cart = customer.getCart();

        return cart.getTotalPrice();
    }


    @Override
    public BigDecimal getCartAveragePrice(Long customerId) {
        Optional<Customer> customerOptional = repository.findById(customerId);

        Customer customer = customerOptional.orElseThrow(() -> new RuntimeException("Customer with id " + customerId + " not found"));

        return customer.getCart().getAveragePrice();
    }

    @Transactional
    @Override
    public void addProductToCart(Long customerId, Long productId) {
        Optional<Customer> customerOptional = repository.findById(customerId);

        Customer customer = customerOptional.orElseThrow(() -> new RuntimeException("Customer with id " + customerId + " not found"));

        Optional<Product> productOptional = productRepository.findById(productId);

        Product product = productOptional.orElseThrow(() -> new RuntimeException("Product with id " + productId + " not found"));

        customer.getCart().addProduct(product);

    }

    @Override
    public void removeProductFromCart(Long customerId, Long productId) {

        // TODO

    }

    @Override
    public void clearCustomerCart(Long customerId) {

        // TODO

    }
}
