package com.kas.service;

import com.kas.dto.PembelianDTO;
import com.kas.dto.ShowPembelianDetailResponse;
import com.kas.entity.Pembelian;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PembelianService {
    void Save(PembelianDTO pembelianDTO);
    List<Pembelian> getPembelians();
    Page<Pembelian> getPembelians(Pageable pageable, String tglFrom, String tglTo);
    ShowPembelianDetailResponse getShowPembelianDetail(Long id);
    void DeletePembelianDetail(Long id);
}
