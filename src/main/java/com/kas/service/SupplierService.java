package com.kas.service;

import com.kas.dto.ResponseSearch;
import com.kas.dto.SupplierCreateDTO;
import com.kas.dto.SupplierUpdateDTO;
import com.kas.entity.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> getSuppliers();
    void Save(SupplierCreateDTO supplierCreateDTO);
    void Update(SupplierUpdateDTO supplierUpdateDTO);
    void Delete(Long id);
    Supplier findById(Long id);
    List<ResponseSearch> findCustomerByName(String q);
}
