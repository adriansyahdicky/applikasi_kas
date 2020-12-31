package com.kas.repository;

import com.kas.entity.Penjualan_Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PenjualanDetailRepository extends JpaRepository<Penjualan_Detail, Long> {

    @Query(value = "select * from penjualan_detail where penjualan_id=:id", nativeQuery = true)
    List<Penjualan_Detail> getByPenjualanId(@Param("id") Long id);

    @Query(value = "SELECT SUM(harga*qty) AS total_pembelian FROM penjualan_detail", nativeQuery = true)
    Double sumTotal();

}
