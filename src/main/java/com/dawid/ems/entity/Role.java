package com.dawid.ems.entity;

import com.dawid.ems.enumeration.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Enumerated
    @Column(name = "role")
    private Roles roles;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role=" + roles +
                '}';
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Roles getRole() {
        return roles;
    }

    public void setRole(Roles role) {
        this.roles = role;
    }
}
