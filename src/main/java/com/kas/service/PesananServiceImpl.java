package com.kas.service;

import com.kas.constant.StatusConstant;
import com.kas.dto.PesananDTO;
import com.kas.dto.PesananDetailDTO;
import com.kas.dto.ShowPesananDetailResponse;
import com.kas.entity.Customer;
import com.kas.entity.LogStatus;
import com.kas.entity.Pesanan;
import com.kas.entity.Pesanan_Detail;
import com.kas.exception.ResourceNotFoundException;
import com.kas.repository.CustomerRepository;
import com.kas.repository.LogStatusRepository;
import com.kas.repository.PesananDetailRepository;
import com.kas.repository.PesananRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class PesananServiceImpl implements PesananService{

    @Autowired
    private PesananRepository pesananRepository;

    @Autowired
    private PesananDetailRepository pesananDetailRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LogStatusRepository logStatusRepository;

    @Override
    public void Save(PesananDTO pesananDTO) {
        Customer customer =  customerRepository.findById(pesananDTO.getCustomer())
                .orElseThrow(() -> new ResourceNotFoundException("Data Customer Tidak Ditemukan"));

        Pesanan pesanan = new Pesanan();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        pesanan.setTanggal(timestamp);
        pesanan.setCustomer(customer);
        pesanan.setStatus(StatusConstant.NEW);
        pesananRepository.save(pesanan);

        for(PesananDetailDTO detail: pesananDTO.getPesananDetailDTOS()){

            Pesanan_Detail pesananDetail = new Pesanan_Detail();
            pesananDetail.setPesanan(pesanan);
            pesananDetail.setNameBarang(detail.getNameBarang());
            pesananDetail.setQty(detail.getQty());
            pesananDetailRepository.save(pesananDetail);
        }

        LogStatus logStatus = new LogStatus();
        logStatus.setCustomer(customer);
        logStatus.setTanggal(timestamp);
        logStatus.setStatus(StatusConstant.NEW);
        logStatusRepository.save(logStatus);

    }

    @Override
    public List<Pesanan> getPesanans() {
        return pesananRepository.findAll();
    }

    @Override
    public Page<Pesanan> getPesanans(Pageable pageable, String tglFrom, String tglTo) {
        return pesananRepository.findByTanggal(pageable, tglFrom, tglTo);
    }

    @Override
    public ShowPesananDetailResponse getShowPesananDetail(Long id) {

        ShowPesananDetailResponse showPesananDetailResponse = new ShowPesananDetailResponse();

        Pesanan pesanan =  pesananRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data Pesanan Tidak Ditemukan"));

        showPesananDetailResponse.setCustomer(pesanan.getCustomer().getNameCustomer());
        showPesananDetailResponse.setTanggal(pesanan.getTanggal());

        showPesananDetailResponse.setPesanan_details(pesananDetailRepository.getByPesananId(id));
        return showPesananDetailResponse;
    }
}
