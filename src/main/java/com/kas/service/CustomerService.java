package com.kas.service;

import com.kas.dto.CustomerCreateDTO;
import com.kas.dto.CustomerUpdateDTO;
import com.kas.dto.ResponseSearch;
import com.kas.entity.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getCustomers();
    void Save(CustomerCreateDTO customerCreateDTO);
    void Update(CustomerUpdateDTO customerUpdateDTO);
    void Delete(Long id);
    Customer findById(Long id);
    List<ResponseSearch> findCustomerByName(String q);
}
