package com.crossasyst.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class AddressEntity {

    @Id
    private Long id;
    private String address;
    private String city;
    private String state;
    private Long zipcode;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    private PersonEntity person;
}
