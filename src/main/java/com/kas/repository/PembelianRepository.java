package com.kas.repository;

import com.kas.constant.StatusConstant;
import com.kas.entity.Pembelian;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PembelianRepository extends JpaRepository<Pembelian, Long> {
    @Override
    @Query(value = "select * from pembelian where status='"+StatusConstant.PROCESS_PEMBELIAN+"'", nativeQuery = true)
    List<Pembelian> findAll();

    @Query(value = "select * from pembelian where DATE(tanggal) between :tglFrom and :tglTo and status='"+StatusConstant.PROCESS_PEMBELIAN+"'", nativeQuery = true)
    Page<Pembelian> findByTanggal(Pageable pageable, @Param("tglFrom") String tglFrom, @Param("tglTo") String tglTo);
}
