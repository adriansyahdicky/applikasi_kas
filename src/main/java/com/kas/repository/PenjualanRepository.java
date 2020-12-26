package com.kas.repository;

import com.kas.constant.StatusConstant;
import com.kas.entity.Penjualan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PenjualanRepository extends JpaRepository<Penjualan, Long> {

    @Override
    @Query(value = "select * from penjualan where status='"+ StatusConstant.PROCESS_PENJUALAN +"'", nativeQuery = true)
    List<Penjualan> findAll();

    @Query(value = "select * from penjualan where DATE(tanggal) between :tglFrom and :tglTo and status='"+StatusConstant.PROCESS_PENJUALAN+"'", nativeQuery = true)
    Page<Penjualan> findByTanggal(Pageable pageable, @Param("tglFrom") String tglFrom, @Param("tglTo") String tglTo);
}
