package com.kas.service;

import com.kas.dto.PengeluaranDTO;
import com.kas.entity.Penerimaan;
import com.kas.entity.Pengeluaran;
import com.kas.repository.PembelianDetailRepository;
import com.kas.repository.PenerimaanRepository;
import com.kas.repository.PengeluaranRepository;
import com.kas.repository.PenjualanDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PengeluaranServiceImpl implements PengeluaranService{

    @Autowired
    private PengeluaranRepository pengeluaranRepository;

    @Autowired
    private PenerimaanRepository penerimaanRepository;

    @Autowired
    private PenjualanDetailRepository penjualanDetailRepository;

    @Autowired
    private PembelianDetailRepository pembelianDetailRepository;

    @Override
    public void Save(PengeluaranDTO pengeluaranDTO) {
        Pengeluaran pengeluaran = new Pengeluaran();
        pengeluaran.setTotal(pengeluaranDTO.getTotal());
        pengeluaran.setKeterangan(pengeluaranDTO.getKeterangan());
        pengeluaran.setIdpembelian(0L);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        pengeluaran.setTanggal(timestamp);
        pengeluaranRepository.save(pengeluaran);
    }

    @Override
    public Page<Pengeluaran> getPengeluarans(Pageable pageable, String tglFrom, String tglTo) {
        return pengeluaranRepository.findByTanggal(pageable, tglFrom, tglTo);
    }

    @Override
    public Page<Penerimaan> getPenerimaans(Pageable pageable, String tglFrom, String tglTo) {
        return penerimaanRepository.findByTanggal(pageable, tglFrom, tglTo);
    }

    @Override
    public List<Pengeluaran> getPengeluarans() {
        return pengeluaranRepository.findAll();
    }

    @Override
    public Double totalLaba() {
        //Double total_pembelian = pembelianDetailRepository.sumTotal();
        Double total_pengeluaran = pengeluaranRepository.sumTotal();
        Double total_penjualan = penjualanDetailRepository.sumTotal();

        return total_penjualan - total_pengeluaran;
    }
}
