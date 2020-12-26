package com.kas.repository;

import com.kas.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "select * from customer where name_customer like %:name%", nativeQuery = true)
    Optional<Customer> findByName(String name);

    @Query(value = "select * from customer where name_customer like %:name%", nativeQuery = true)
    List<Customer> findByLikeName(String name);
}
