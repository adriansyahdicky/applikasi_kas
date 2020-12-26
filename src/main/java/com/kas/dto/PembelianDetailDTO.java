package com.kas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PembelianDetailDTO {

    private String nameBarang;
    private int qty;
    private double harga;
    private Long supplier;
}
