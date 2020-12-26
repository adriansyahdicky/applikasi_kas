package com.kas.service;

import com.kas.dto.ResponseSearch;
import com.kas.dto.SupplierCreateDTO;
import com.kas.dto.SupplierUpdateDTO;
import com.kas.entity.Supplier;
import com.kas.exception.ResourceIsExistingException;
import com.kas.exception.ResourceNotFoundException;
import com.kas.repository.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class SupplierServiveImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public List<Supplier> getSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public void Save(SupplierCreateDTO supplierCreateDTO) {
        Optional<Supplier> findExistName = supplierRepository.findByName(supplierCreateDTO.getNameSupplier());
        if (findExistName.isPresent()){
            throw new ResourceIsExistingException(supplierCreateDTO.getNameSupplier()+" " +
                    "is existing in our database !");
        }
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(supplierCreateDTO, supplier);
        supplierRepository.save(supplier);
    }

    @Override
    public void Update(SupplierUpdateDTO supplierUpdateDTO) {
        Supplier supplier =  supplierRepository.findById(supplierUpdateDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Data Supplier Tidak Ditemukan"));
        BeanUtils.copyProperties(supplierUpdateDTO, supplier);
        supplierRepository.save(supplier);
    }

    @Override
    public void Delete(Long id) {
        Supplier supplier =  supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data Supplier Tidak Ditemukan"));
        supplierRepository.delete(supplier);
    }

    @Override
    public Supplier findById(Long id) {
        Supplier supplier =  supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data Supplier Tidak Ditemukan"));
        return supplier;
    }

    @Override
    public List<ResponseSearch> findCustomerByName(String q) {
        List<Supplier> listSupplier =  supplierRepository.findByLikeName(q);
        List<ResponseSearch> searchSupplier = new ArrayList<>();

        listSupplier.forEach(customer -> {
            ResponseSearch rs = new ResponseSearch();
            rs.setId(customer.getId());
            rs.setText(customer.getNameSupplier());
            searchSupplier.add(rs);
        });

        if (searchSupplier.isEmpty() && searchSupplier.size() <0){
            if (log.isInfoEnabled()){
                log.debug("{\"method\" : \"findCustomerByName\", " +
                        "\"error\" : \"error because data is null\"}");
            }
        }

        return  searchSupplier;
    }
}
