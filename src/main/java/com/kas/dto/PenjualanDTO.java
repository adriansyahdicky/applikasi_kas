package com.kas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PenjualanDTO {

    private Long idpembelian;
    private String tipe_pembayaran;

}
