package com.springadmin.springadmin.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.springadmin.springadmin.annotations.AdminEntity;
import com.springadmin.springadmin.models.TestEntity;

@Entity
@AdminEntity
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
