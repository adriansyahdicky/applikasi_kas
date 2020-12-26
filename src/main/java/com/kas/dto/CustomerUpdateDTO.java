package com.kas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateDTO {

    private Long id;
    @NotBlank(message = "Nama Customer Tidak Boleh Kosong")
    private String nameCustomer;
    @NotBlank(message = "No Handphone Tidak Boleh Kosong")
    @Size(max = 12, message = "No Handphone tidak boleh lebih dari 12")
    private String noHandphone;
    @NotBlank(message = "Alamat Tidak Boleh Kosong")
    private String alamat;
    @NotBlank(message = "No Rekening Tidak Boleh Kosong")
    private String noRekening;

}
