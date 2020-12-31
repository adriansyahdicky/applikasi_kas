package com.kas.service;

import com.kas.constant.StatusConstant;
import com.kas.dto.PembelianDTO;
import com.kas.dto.PembelianDetailDTO;
import com.kas.dto.ShowPembelianDetailResponse;
import com.kas.entity.*;
import com.kas.exception.ResourceNotFoundException;
import com.kas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PembelianServiceImpl implements PembelianService{

    @Autowired
    private PembelianRepository pembelianRepository;

    @Autowired
    private PembelianDetailRepository pembelianDetailRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private PesananRepository pesananRepository;

    @Autowired
    private PengeluaranRepository pengeluaranRepository;

    @Autowired
    private LogStatusRepository logStatusRepository;

    @Autowired
    private PesananDetailRepository pesananDetailRepository;

    @Override
    public void Save(PembelianDTO pembelianDTO) {
        Pesanan pesanan =  pesananRepository.findById(pembelianDTO.getIdpesanan())
                .orElseThrow(() -> new ResourceNotFoundException("Data Pesanan Tidak Ditemukan"));

        Pembelian pembelian = new Pembelian();
        pembelian.setTanggal(pesanan.getTanggal());
        pembelian.setPesanan(pesanan);
        pembelian.setStatus(StatusConstant.PROCESS_PEMBELIAN);
        pembelianRepository.save(pembelian);

        pesanan.setStatus(StatusConstant.PROCESS_PEMBELIAN);
        pesananRepository.save(pesanan);

        double totalHargaBeli = 0;
        for(PembelianDetailDTO detailDTO: pembelianDTO.getPembelianDetailDTOS()) {
            Pembelian_Detail pembelian_detail = new Pembelian_Detail();
            pembelian_detail.setPembelian(pembelian);
            pembelian_detail.setNameBarang(detailDTO.getNameBarang());
            pembelian_detail.setQty(detailDTO.getQty());
            pembelian_detail.setHarga(detailDTO.getHarga());
            Supplier supplier =  supplierRepository.findById(detailDTO.getSupplier())
                    .orElseThrow(() -> new ResourceNotFoundException("Data Supplier Tidak Ditemukan"));
            pembelian_detail.setSupplier(supplier);
            pembelianDetailRepository.save(pembelian_detail);
            totalHargaBeli += detailDTO.getHarga() * detailDTO.getQty();
        }

        Pengeluaran pengeluaran = new Pengeluaran();
        pengeluaran.setTanggal(pesanan.getTanggal());
        pengeluaran.setIdpembelian(pembelian.getId());
        pengeluaran.setKeterangan(StatusConstant.KETERANGAN);
        pengeluaran.setTotal(totalHargaBeli);
        pengeluaranRepository.save(pengeluaran);

        LogStatus logStatus = new LogStatus();
        logStatus.setCustomer(pesanan.getCustomer());
        logStatus.setTanggal(pesanan.getTanggal());
        logStatus.setStatus(StatusConstant.PROCESS_PEMBELIAN);
        logStatusRepository.save(logStatus);
    }

    @Override
    public List<Pembelian> getPembelians() {
        return pembelianRepository.findAll();
    }

    @Override
    public Page<Pembelian> getPembelians(Pageable pageable, String tglFrom, String tglTo) {
        return pembelianRepository.findByTanggal(pageable, tglFrom, tglTo);
    }

    @Override
    public ShowPembelianDetailResponse getShowPembelianDetail(Long id) {
        ShowPembelianDetailResponse showPembelianDetailResponse = new ShowPembelianDetailResponse();

        Pembelian pembelian =  pembelianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data Pembelian Tidak Ditemukan"));

        showPembelianDetailResponse.setCustomer(pembelian.getPesanan().getCustomer().getNameCustomer());
        showPembelianDetailResponse.setTanggal(pembelian.getPesanan().getTanggal());

        List<Pembelian_Detail> setDetailPembelian = new ArrayList<>();

        for(Pembelian_Detail pd : pembelianDetailRepository.getByPembelianId(id)){
            Pembelian_Detail pembelian_detail = new Pembelian_Detail();
            pembelian_detail.setId(pd.getId());
            pembelian_detail.setPembelian(pd.getPembelian());
            pembelian_detail.setSupplier(pd.getSupplier());
            double hargaTotalPajak = (pd.getHarga() * 25) / 100;
            pembelian_detail.setHarga(pd.getHarga() + hargaTotalPajak);
            pembelian_detail.setQty(pd.getQty());
            pembelian_detail.setNameBarang(pd.getNameBarang());
            setDetailPembelian.add(pembelian_detail);
        }

        showPembelianDetailResponse.setPembelian_details(setDetailPembelian);
        return showPembelianDetailResponse;
    }

    @Override
    public void DeletePembelianDetail(Long id) {
        Pesanan_Detail pesanan_detail =  pesananDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data Pesanan Detail Tidak Ditemukan"));
        pesananDetailRepository.delete(pesanan_detail);
    }
}
