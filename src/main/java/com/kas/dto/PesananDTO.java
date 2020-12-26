package com.kas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PesananDTO {

    private String tanggal;
    private Long customer;
    private List<PesananDetailDTO> pesananDetailDTOS;


}

