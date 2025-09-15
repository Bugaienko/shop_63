package ait.cohor63.shop.service.interfaces;

import ait.cohor63.shop.model.dto.CustomerDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Sergey Bugaenko
 * {@code @date} 15.09.2025
 */


public interface CustomerService {

    //    Сохранить покупателя в базе данных (при сохранении покупатель автоматически считается активным)
    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    //    Вернуть всех покупателей из базы данных (активных)
    List<CustomerDTO> getAllActiveCustomers();

//    Вернуть одного покупателя из базы данных по его идентификатору (если он активен)

    CustomerDTO getCustomerById(Long id);

    //    Изменить одного покупателя в базе данных по его идентификатору
    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);

    //    Удалить покупателя из базы данных по его идентификатору
    void deleteCustomerById(Long id);

    //    Удалить покупателя из базы данных по его имени
    void deleteCustomerByName(String name);

    //    Восстановить удалённого покупателя в базе данных по его идентификатору
    CustomerDTO restoreCustomerById(Long id);

    //    Вернуть общее количество покупателей в базе данных (активных)
    long getActiveCustomerCount();

    //    Вернуть стоимость корзины покупателя по его идентификатору (если он активен)
    BigDecimal getCartTotalPrice(Long customerId);

    //    Вернуть среднюю стоимость продукта в корзине покупателя по его идентификатору (если он активен)
    BigDecimal getCartAveragePrice(Long customerId);

    //    Добавить товар в корзину покупателя по их идентификаторам (если оба активны)
    void addProductToCart(Long customerId, Long productId);

    //    Удалить товар из корзины покупателя по их идентификаторам
    void removeProductFromCart(Long customerId, Long productId);

    //    Полностью очистить корзину покупателя по его идентификатору (если он активен)
    void clearCustomerCart(Long customerId);


}
