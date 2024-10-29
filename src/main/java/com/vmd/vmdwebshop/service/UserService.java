package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.model.Customer;
import com.vmd.vmdwebshop.repository.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(HttpServletRequest request) {
        Customer customer = new Customer(request.getSession().getId(), true);
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(long id) {
        return customerRepository.findById(id).orElse(null);
    }


}
