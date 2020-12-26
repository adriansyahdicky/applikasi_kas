package com.kas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PengeluaranDTO {

    @NotBlank(message = "Keterangan tidak boleh kosong")
    private String keterangan;
    @NotBlank(message = "Tanggal tidak boleh kosong")
    private String tanggal;
    @NotNull(message = "Total tidak boleh kosong")
    private double total;

}
