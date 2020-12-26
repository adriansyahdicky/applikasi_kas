package com.kas.repository;

import com.kas.entity.Customer;
import com.kas.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    @Query(value = "select * from supplier where name_supplier like %:name%", nativeQuery = true)
    Optional<Supplier> findByName(String name);

    @Query(value = "select * from supplier where name_supplier like %:name%", nativeQuery = true)
    List<Supplier> findByLikeName(String name);
}
