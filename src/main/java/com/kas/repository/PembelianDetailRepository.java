package com.kas.repository;

import com.kas.entity.Pembelian_Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PembelianDetailRepository extends JpaRepository<Pembelian_Detail, Long> {

    @Query(value = "select * from pembelian_detail where pembelian_id=:id", nativeQuery = true)
    List<Pembelian_Detail> getByPembelianId(@Param("id") Long id);

    @Query(value = "SELECT SUM(harga*qty) AS total_pembelian FROM pembelian_detail", nativeQuery = true)
    Double sumTotal();
}
