package com.debuggeandoideas.customer_manager.services;

import com.debuggeandoideas.customer_manager.enums.UpdateRoleOperation;
import com.debuggeandoideas.customer_manager.repositories.CustomerRepository;
import com.debuggeandoideas.customer_manager.repositories.RoleRepository;
import com.debuggeandoideas.customer_manager.tables.CustomerTable;
import com.debuggeandoideas.customer_manager.tables.RoleTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final DatabaseClient databaseClient;

    @Override
    public Mono<CustomerTable> createCustomer(CustomerTable customerTable) {
        return null;
    }

    @Override
    public Mono<Map<String, List<RoleTable>>> readRolesByEmail(String email) {
        return null;
    }

    @Override
    public Mono<Void> deleteCustomer(Long id) {
        return null;
    }

    @Override
    public Mono<CustomerTable> updateRoleInCustomer(Long id, List<String> roleName, UpdateRoleOperation operation) {
        return null;
    }

    private static final String FIND_BY_ROLE_QUERY = """
            SELECT r.name, r.description
            FROM role r
            INNER JOIN customer_role cr ON r.id = cr.role_id
            WHERE cr.customer_id = :customerId
            """;
}