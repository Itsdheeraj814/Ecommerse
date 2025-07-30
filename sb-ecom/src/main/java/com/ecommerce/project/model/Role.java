package com.ecommerce.project.model;

import jakarta.persistence.*;


import javax.annotation.processing.Generated;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @Enumerated(EnumType.STRING)
    @Column(length=20,name = "role_name")
    private AppRole roleName;

    public Role(AppRole roleName) {
        this.roleName = roleName;
    }
    public Role(Integer roleId) {
        this.roleId = roleId;
    }
    public Integer getRoleId() {
        return roleId;
    }
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
    public AppRole getRoleName() {
        return roleName;
    }
    public void setRoleName(AppRole roleName) {
        this.roleName = roleName;
    }
    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName=" + roleName +
                '}';
    }
}
