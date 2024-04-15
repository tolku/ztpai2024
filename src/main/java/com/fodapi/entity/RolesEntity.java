package com.fodapi;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "roles", schema = "public", catalog = "projektztpai")
public class RolesEntity {
    @Basic
    @Column(name = "role")
    private String role;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolesEntity that = (RolesEntity) o;
        return Objects.equals(role, that.role) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, id);
    }
}
