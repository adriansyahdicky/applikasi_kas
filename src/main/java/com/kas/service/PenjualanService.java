package com.kas.service;

import com.kas.dto.PenjualanDTO;
import com.kas.entity.Penjualan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PenjualanService {
    void Save(PenjualanDTO penjualanDTO);
    Page<Penjualan> getPenjualans(Pageable pageable, String tglFrom, String tglTo);
}
