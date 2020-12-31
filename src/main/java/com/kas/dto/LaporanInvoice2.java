package com.kas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaporanInvoice2 {

    private Long id;
    private Timestamp tanggal;
    private Double harga;
    private String name_barang;
    private int qty;
    private String alamat;
    private String name_customer;

}
