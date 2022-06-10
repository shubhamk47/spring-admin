package com.springadmin.springadmin.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.springadmin.springadmin.annotations.AdminEntity;

@AdminEntity
@Entity
public class TestEntity {

    @Id
    @GeneratedValue
    int id;
    String name;
    boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public TestEntity() {
    }

}
