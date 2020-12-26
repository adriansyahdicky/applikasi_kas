package com.kas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kas.entity.Pesanan_Detail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowPesananDetailResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp tanggal;

    private String customer;

    private List<Pesanan_Detail> pesanan_details;

}
