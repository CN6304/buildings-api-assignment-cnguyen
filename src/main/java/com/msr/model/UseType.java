package com.msr.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class UseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
}
