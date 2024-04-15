package com.fodapi;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "user_roles", schema = "public", catalog = "projektztpai")
public class UserRolesEntity {
    @Basic
    @Column(name = "user_id")
    private String userId;
    @Basic
    @Column(name = "role_id")
    private String roleId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private RolesEntity rolesByRoleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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
        UserRolesEntity that = (UserRolesEntity) o;
        return Objects.equals(userId, that.userId) && Objects.equals(roleId, that.roleId) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId, id);
    }

    public RolesEntity getRolesByRoleId() {
        return rolesByRoleId;
    }

    public void setRolesByRoleId(RolesEntity rolesByRoleId) {
        this.rolesByRoleId = rolesByRoleId;
    }
}
