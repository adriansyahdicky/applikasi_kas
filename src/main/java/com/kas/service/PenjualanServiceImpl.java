package com.kas.service;

import com.kas.constant.StatusConstant;
import com.kas.dto.PenjualanDTO;
import com.kas.entity.*;
import com.kas.exception.ResourceNotFoundException;
import com.kas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PenjualanServiceImpl implements PenjualanService{

    @Autowired
    private PenjualanRepository penjualanRepository;

    @Autowired
    private PenjualanDetailRepository penjualanDetailRepository;

    @Autowired
    private PembelianRepository pembelianRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PembelianDetailRepository pembelianDetailRepository;

    @Autowired
    private LogStatusRepository logStatusRepository;

    @Autowired
    private PesananRepository pesananRepository;

    @Autowired
    private PenerimaanRepository penerimaanRepository;

    @Override
    public void Save(PenjualanDTO penjualanDTO) {
        Pembelian pembelian =  pembelianRepository.findById(penjualanDTO.getIdpembelian())
                .orElseThrow(() -> new ResourceNotFoundException("Data Pembelian Tidak Ditemukan"));
        pembelian.setStatus(StatusConstant.PROCESS_PENJUALAN);
        pembelianRepository.save(pembelian);


        Penjualan penjualan = new Penjualan();
        penjualan.setPembelian(pembelian);
        penjualan.setTanggal(pembelian.getTanggal());
        penjualan.setCustomer(pembelian.getPesanan().getCustomer());
        penjualan.setStatus(StatusConstant.PROCESS_PENJUALAN);
        penjualanRepository.save(penjualan);

        List<Pembelian_Detail> details = pembelianDetailRepository.getByPembelianId(pembelian.getId());
        double totalhargaJual = 0;
        for(Pembelian_Detail detailDTO:details) {
            Penjualan_Detail penjualan_detail = new Penjualan_Detail();
            penjualan_detail.setPenjualan(penjualan);
            penjualan_detail.setNameBarang(detailDTO.getNameBarang());
            penjualan_detail.setQty(detailDTO.getQty());
            double hargaJualPajak = (detailDTO.getHarga() * 25) / 100;
            penjualan_detail.setHarga(detailDTO.getHarga() + hargaJualPajak);
            penjualanDetailRepository.save(penjualan_detail);
            totalhargaJual += (detailDTO.getHarga() + hargaJualPajak) * detailDTO.getQty();
        }

        Penerimaan penerimaan = new Penerimaan();
        penerimaan.setPenjualan(penjualan);
        penerimaan.setCustomer(pembelian.getPesanan().getCustomer());
        penerimaan.setNominal(totalhargaJual);
        penerimaan.setTipePembayaran(penjualanDTO.getTipe_pembayaran());
        penerimaanRepository.save(penerimaan);

        Pesanan pesanan =  pesananRepository.findById(pembelian.getPesanan().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Data Pesanan Tidak Ditemukan"));
        pesanan.setStatus(StatusConstant.PROCESS_PENJUALAN);
        pesananRepository.save(pesanan);

        LogStatus logStatus = new LogStatus();
        logStatus.setCustomer(pembelian.getPesanan().getCustomer());
        logStatus.setTanggal(pembelian.getTanggal());
        logStatus.setStatus(StatusConstant.PROCESS_PENJUALAN);
        logStatusRepository.save(logStatus);
    }

    @Override
    public Page<Penjualan> getPenjualans(Pageable pageable, String tglFrom, String tglTo) {
        return penjualanRepository.findByTanggal(pageable, tglFrom, tglTo);
    }
}
