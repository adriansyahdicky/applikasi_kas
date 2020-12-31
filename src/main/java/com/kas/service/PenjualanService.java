package com.kas.service;

import com.kas.dto.PenjualanDTO;
import com.kas.entity.Penjualan;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.FileNotFoundException;
import java.util.List;

public interface PenjualanService {
    void Save(PenjualanDTO penjualanDTO);
    Page<Penjualan> getPenjualans(Pageable pageable, String tglFrom, String tglTo);
    JasperPrint ReportLaporan(Long id) throws FileNotFoundException, JRException;
    JasperPrint ReportInvoice(Long id) throws FileNotFoundException, JRException;
    JasperPrint ReportKwitansi(Long id) throws FileNotFoundException, JRException;
    List<Penjualan> getPenjualans();
}
