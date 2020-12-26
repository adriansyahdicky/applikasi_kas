package com.kas.service;

import com.kas.dto.PesananDTO;
import com.kas.dto.ShowPesananDetailResponse;
import com.kas.entity.Pesanan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PesananService {

    void Save(PesananDTO pesananDTO);
    List<Pesanan> getPesanans();
    Page<Pesanan> getPesanans(Pageable pageable, String tglFrom, String tglTo);
    ShowPesananDetailResponse getShowPesananDetail(Long id);

}
