package com.example.spring_boot_react_demo.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class People {
    @Id
    private Long id ;
}
