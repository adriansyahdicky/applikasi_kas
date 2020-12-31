package com.kas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kas.entity.Pembelian_Detail;
import com.kas.entity.Penjualan_Detail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LaporanInvoice {

    private Long id;
    private String name_customer;
    private String name_barang;
    private int qty;
    private String no_handphone;
    private String alamat;
    private Timestamp tanggal;
    private Double harga;


}
