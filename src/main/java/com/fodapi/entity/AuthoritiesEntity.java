package com.fodapi.entity;

import jakarta.persistence.*;

@Table(name = "authorities")
public class AuthoritiesEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;

    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "authority")
    private String authority;
}
