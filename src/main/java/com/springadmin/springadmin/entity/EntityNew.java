package com.springadmin.springadmin.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.springadmin.springadmin.annotations.AdminEntity;

@Entity
@AdminEntity
public class EntityNew {

    @Id
    int id;
    String test;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public EntityNew() {
    }

    @Override
    public String toString() {
        return "EntityNew [id=" + id + ", test=" + test + "]";
    }

}
