package com.springadmin.springadmin.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.springadmin.springadmin.entity.EntityNew;

import javax.persistence.*;

@Entity
public class TestEntity {

    @Id
    @GeneratedValue
    int id;
    String name;
    boolean active;
    @ManyToOne
    @JoinColumn(name = "entity_new_id", nullable = false)
    @JsonBackReference
    EntityNew entityNew;

    public TestEntity() {
    }

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

    public EntityNew getEntityNew() {
        return entityNew;
    }

    public void setEntityNew(EntityNew entityNew) {
        this.entityNew = entityNew;
    }

    @Override
    public String toString() {
        return "TestEntity [active=" + active + ", entityNew=" + entityNew.toString() + ", id=" + id + ", name=" + name
                + "]";
    }

}
