package com.kas.service;

import com.kas.constant.StatusConstant;
import com.kas.dto.*;
import com.kas.entity.*;
import com.kas.exception.ResourceNotFoundException;
import com.kas.repository.*;
import com.kas.utils.UtilTerbilang;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public JasperPrint ReportLaporan(Long id) throws FileNotFoundException, JRException {

        List list = new ArrayList();

        for(Penjualan_Detail pd : penjualanDetailRepository.getByPenjualanId(id)){

            LaporanInvoice laporanInvoice = new LaporanInvoice();
            Penjualan penjualan =  penjualanRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data Penjualan Tidak Ditemukan"));
            laporanInvoice.setName_customer(penjualan.getCustomer().getNameCustomer());
            laporanInvoice.setId(id);
            laporanInvoice.setNo_handphone(penjualan.getCustomer().getNoHandphone());
            laporanInvoice.setAlamat(penjualan.getCustomer().getAlamat());
            laporanInvoice.setTanggal(penjualan.getTanggal());
            laporanInvoice.setHarga(pd.getHarga());

            laporanInvoice.setName_barang(pd.getNameBarang());
            laporanInvoice.setQty(pd.getQty());

            list.add(laporanInvoice);
        }


        File file = ResourceUtils.getFile("classpath:Invoice.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, null, dataSource);

        return print;
    }

    @Override
    public JasperPrint ReportInvoice(Long id) throws FileNotFoundException, JRException {
        List list = new ArrayList();

        for(Penjualan_Detail pd : penjualanDetailRepository.getByPenjualanId(id)){

            LaporanInvoice2 laporanInvoice = new LaporanInvoice2();
            Penjualan penjualan =  penjualanRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data Penjualan Tidak Ditemukan"));
            laporanInvoice.setAlamat(penjualan.getCustomer().getAlamat());
            laporanInvoice.setId(id);
            laporanInvoice.setName_customer(penjualan.getCustomer().getNameCustomer());
            laporanInvoice.setTanggal(penjualan.getTanggal());

            laporanInvoice.setName_barang(pd.getNameBarang());
            laporanInvoice.setQty(pd.getQty());
            laporanInvoice.setHarga(pd.getHarga());

            list.add(laporanInvoice);
        }


        File file = ResourceUtils.getFile("classpath:Invoice2.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, null, dataSource);

        return print;
    }

    @Override
    public JasperPrint ReportKwitansi(Long id) throws FileNotFoundException, JRException {
        List list = new ArrayList();


            LaporanKwitansi laporanInvoice = new LaporanKwitansi();
            Penjualan penjualan =  penjualanRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data Penjualan Tidak Ditemukan"));
            laporanInvoice.setId(id);
            laporanInvoice.setName_customer(penjualan.getCustomer().getNameCustomer());
            laporanInvoice.setTanggal(penjualan.getTanggal());
            laporanInvoice.setAlamat(penjualan.getCustomer().getAlamat());
            laporanInvoice.setNo_handphone(penjualan.getCustomer().getNoHandphone());

            Penerimaan penerimaan =  penerimaanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data Penjualan Tidak Ditemukan"));

            laporanInvoice.setNominal(penerimaan.getNominal());
            String terbilang = UtilTerbilang.terbilang(BigDecimal.valueOf(penerimaan.getNominal()));

            list.add(laporanInvoice);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("banyak",terbilang);

        File file = ResourceUtils.getFile("classpath:Kwitansi.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return print;
    }

    @Override
    public List<Penjualan> getPenjualans() {
        return penjualanRepository.findAll();
    }
}
