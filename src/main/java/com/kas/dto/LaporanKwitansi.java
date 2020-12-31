package com.kas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaporanKwitansi {

    private Long id;
    private Timestamp tanggal;
    private String name_customer;
    private Double nominal;
    private String no_handphone;
    private String alamat;

}
