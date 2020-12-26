package com.kas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierUpdateDTO {
    private Long id;
    @NotBlank(message = "Nama Supplier Tidak Boleh Kosong")
    private String nameSupplier;
    @NotBlank(message = "No Handphone Tidak Boleh Kosong")
    @Size(max = 12, message = "No Handphone tidak boleh lebih dari 12")
    private String noHandphone;
    @NotBlank(message = "Alamat Tidak Boleh Kosong")
    private String alamat;
}
