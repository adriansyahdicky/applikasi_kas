package com.kas.repository;

import com.kas.entity.Pengeluaran;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PengeluaranRepository extends JpaRepository<Pengeluaran, Long> {

    @Query(value = "select * from pengeluaran where DATE(tanggal) between :tglFrom and :tglTo ", nativeQuery = true)
    Page<Pengeluaran> findByTanggal(Pageable pageable, @Param("tglFrom") String tglFrom, @Param("tglTo") String tglTo);

    @Query(value = "SELECT SUM(total) AS total_pengeluaran FROM pengeluaran", nativeQuery = true)
    Double sumTotal();

}
