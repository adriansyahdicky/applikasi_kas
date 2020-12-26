package com.kas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PembelianDTO {

    private Long idpesanan;

    private List<PembelianDetailDTO> pembelianDetailDTOS;

}
