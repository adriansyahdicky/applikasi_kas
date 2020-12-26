package com.kas.repository;

import com.kas.entity.Pesanan_Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PesananDetailRepository extends JpaRepository<Pesanan_Detail, Long> {

    @Query(value = "select * from pesanan_detail where pesanan_id=:id", nativeQuery = true)
    List<Pesanan_Detail> getByPesananId(@Param("id") Long id);

}
