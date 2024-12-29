package com.phatdo.ecommerce.arena.customer.service;

import com.phatdo.ecommerce.arena.account.domain.Account;
import com.phatdo.ecommerce.arena.account.request.RegisterDTO;
import com.phatdo.ecommerce.arena.customer.domain.Customer;
import com.phatdo.ecommerce.arena.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createNewCustomer(Account account, RegisterDTO form) {
        Customer customer = new Customer(account);
        customer.setAvatar("https://t4.ftcdn.net/jpg/05/49/98/39/360_F_549983970_bRCkYfk0P6PP5fKbMhZMIb07mCJ6esXL.jpg");
        customer.setFullName(form.fullName());
        customer.setTelephone(form.telephone());
        customer.setCountry(form.country());

        return customerRepository.save(customer);
    }
}
