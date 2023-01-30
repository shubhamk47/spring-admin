package com.springadmin.springadmin.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.springadmin.springadmin.models.TestEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class EntityNew {

    @Id
    @GeneratedValue
    int id;
    String test;
    @OneToOne
    @JoinColumn(name = "entity_test_id", referencedColumnName = "id")
    EntityTest entityTest;
    @OneToMany(mappedBy = "entityNew")
    Set<TestEntity> testEntities;

    public EntityNew() {
    }

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

    public EntityTest getEntityTest() {
        return entityTest;
    }

    public void setEntityTest(EntityTest entityTest) {
        this.entityTest = entityTest;
    }

    @JsonManagedReference
    public Set<TestEntity> getTestEntities() {
        return testEntities;
    }

    public void setTestEntities(Set<TestEntity> testEntities) {
        this.testEntities = testEntities;
    }

    @Override
    public String toString() {
        return "EntityNew [entityTest=" + entityTest.toString() + ", id=" + id + ", test=" + test + ", testEntities="
                + testEntities.toString() + "]";
    }

}
