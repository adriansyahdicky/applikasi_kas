package com.kas.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pesanan_detail")
public class Pesanan_Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_barang")
    private String nameBarang;

    @Column(name = "qty")
    private int qty;

    @ManyToOne
    @JoinColumn(name = "pesanan_id", nullable = false)
    private Pesanan pesanan;
}
