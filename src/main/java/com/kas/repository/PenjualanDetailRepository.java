package com.kas.repository;

import com.kas.entity.Penjualan_Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenjualanDetailRepository extends JpaRepository<Penjualan_Detail, Long> {
}
