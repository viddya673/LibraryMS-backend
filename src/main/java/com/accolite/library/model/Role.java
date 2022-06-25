package com.accolite.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.*;
import java.util.List;

@EnableJpaRepositories
@Entity
public class Role {
    @Id
    private int rid;
    private String role;

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public Role() {
    }

    public Role(int rid, String role) {
        this.rid = rid;
        this.role = role;
        //this.user = user;
    }

    @Override
    public String toString() {
        return "Role{" +
                "rid=" + rid +
                ", role='" + role + '\'' +
                //", user=" + user +
                '}';
    }
}
