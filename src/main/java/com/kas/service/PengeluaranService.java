package com.kas.service;

import com.kas.dto.PengeluaranDTO;
import com.kas.entity.Penerimaan;
import com.kas.entity.Pengeluaran;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PengeluaranService {
    void Save(PengeluaranDTO pengeluaranDTO);
    Page<Pengeluaran> getPengeluarans(Pageable pageable, String tglFrom, String tglTo);
    Page<Penerimaan> getPenerimaans(Pageable pageable, String tglFrom, String tglTo);
    List<Pengeluaran> getPengeluarans();
}
