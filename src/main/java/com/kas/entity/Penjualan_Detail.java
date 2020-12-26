package com.kas.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "penjualan_detail")
public class Penjualan_Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_barang")
    private String nameBarang;

    @Column(name = "qty")
    private int qty;

    @Column(name = "harga")
    private double harga;

    @ManyToOne
    @JoinColumn(name = "penjualan_id", nullable = false)
    private Penjualan penjualan;

}
