package com.mounirkhalil.DrMaw3ad.models.databaseModels;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "root_admin")
    private Boolean rootAdmin;


    public Integer getAdminId() {
        return adminId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getRootAdmin() {
        return rootAdmin;
    }

    public void setRootAdmin(Boolean rootAdmin) {
        this.rootAdmin = rootAdmin;
    }
}
