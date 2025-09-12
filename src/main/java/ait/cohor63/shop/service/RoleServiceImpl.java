package ait.cohor63.shop.service;

import ait.cohor63.shop.model.entity.Role;
import ait.cohor63.shop.repository.RoleRepository;
import ait.cohor63.shop.service.interfaces.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Bugaenko
 * {@code @date} 12.09.2025
 */

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleUser() {
        // Получаем роль USER из БД
        return roleRepository.findByTitle("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Database doesn't contain role USER"));
    }
}
