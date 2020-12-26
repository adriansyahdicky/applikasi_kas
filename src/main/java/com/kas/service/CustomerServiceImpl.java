package com.kas.service;

import com.kas.dto.CustomerCreateDTO;
import com.kas.dto.CustomerUpdateDTO;
import com.kas.dto.ResponseSearch;
import com.kas.entity.Customer;
import com.kas.exception.ResourceIsExistingException;
import com.kas.exception.ResourceNotFoundException;
import com.kas.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService{



    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void Save(CustomerCreateDTO customerCreateDTO) {
        Optional<Customer> findExistName = customerRepository.findByName(customerCreateDTO.getNameCustomer());
        if (findExistName.isPresent()){
            throw new ResourceIsExistingException(customerCreateDTO.getNameCustomer()+" " +
                    "is existing in our database !");
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerCreateDTO, customer);
        customerRepository.save(customer);
    }

    @Override
    public void Update(CustomerUpdateDTO customerUpdateDTO) {
        Customer customer =  customerRepository.findById(customerUpdateDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Data Customer Tidak Ditemukan"));
        BeanUtils.copyProperties(customerUpdateDTO, customer);
        customerRepository.save(customer);
    }

    @Override
    public void Delete(Long id) {
        Customer customer =  customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data Customer Tidak Ditemukan"));
        customerRepository.delete(customer);
    }

    @Override
    public Customer findById(Long id) {
        Customer customer =  customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data Customer Tidak Ditemukan"));
        return customer;
    }

    @Override
    public List<ResponseSearch> findCustomerByName(String q) {
        List<Customer> listCustomer =  customerRepository.findByLikeName(q);
        List<ResponseSearch> searchCustomer = new ArrayList<>();

        listCustomer.forEach(customer -> {
            ResponseSearch rs = new ResponseSearch();
            rs.setId(customer.getId());
            rs.setText(customer.getNameCustomer());
            searchCustomer.add(rs);
        });

        if (searchCustomer.isEmpty() && searchCustomer.size() <0){
            if (log.isInfoEnabled()){
                log.debug("{\"method\" : \"findCustomerByName\", " +
                        "\"error\" : \"error because data is null\"}");
            }
        }

        return  searchCustomer;
    }
}
