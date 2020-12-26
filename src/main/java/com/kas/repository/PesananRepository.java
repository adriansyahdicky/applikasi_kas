package com.kas.repository;

import com.kas.constant.StatusConstant;
import com.kas.entity.Pesanan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PesananRepository extends JpaRepository<Pesanan, Long> {
    @Override
    @Query(value = "select * from pesanan where status='"+ StatusConstant.NEW +"'", nativeQuery = true)
    List<Pesanan> findAll();

    @Query(value = "select * from pesanan where DATE(tanggal) between :tglFrom and :tglTo and status='"+StatusConstant.NEW+"'", nativeQuery = true)
    Page<Pesanan> findByTanggal(Pageable pageable, @Param("tglFrom") String tglFrom, @Param("tglTo") String tglTo);
}
