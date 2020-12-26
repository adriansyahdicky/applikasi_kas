package com.kas.repository;

import com.kas.entity.Penerimaan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PenerimaanRepository extends JpaRepository<Penerimaan, Long> {

    @Query(value = "select pn.* from penerimaan pn inner join penjualan pj on (pn.penjualan_id=pj.id) where DATE(pj.tanggal) between :tglFrom and :tglTo ", nativeQuery = true)
    Page<Penerimaan> findByTanggal(Pageable pageable, @Param("tglFrom") String tglFrom, @Param("tglTo") String tglTo);

}
