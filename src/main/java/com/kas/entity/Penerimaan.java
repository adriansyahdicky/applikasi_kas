package com.kas.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "penerimaan")
public class Penerimaan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "tipe_pembayaran")
    private String tipePembayaran;

    private double nominal;

    @ManyToOne
    @JoinColumn(name = "penjualan_id", nullable = false)
    private Penjualan penjualan;

}
