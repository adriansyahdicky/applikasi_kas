package com.kas.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_customer")
    private String nameCustomer;

    @Column(name = "no_handphone")
    private String noHandphone;

    @Column(name = "alamat")
    private String alamat;

    @Column(name = "no_rekening")
    private String noRekening;

}
