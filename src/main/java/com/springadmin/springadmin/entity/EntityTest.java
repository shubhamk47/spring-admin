package com.springadmin.springadmin.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.springadmin.springadmin.annotations.AdminEntity;

@AdminEntity
@Entity
public class EntityTest {

    @Id
    @GeneratedValue
    int id;
    String name;
    float weight;

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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public EntityTest() {
    }

}
