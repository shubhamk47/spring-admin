package com.springadmin.springadmin.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.springadmin.springadmin.annotations.AdminEntity;

@AdminEntity
@Entity
public class EntityTest {

    @Id
    @GeneratedValue
    Integer id;
    String name;
    float weight;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    LocalDate createdDate;
    @OneToOne
    @JoinColumn(name="entityTest")
    EntityNew entityNew;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        // this.createdDate = LocalDate.parse(createdDate);
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "EntityTest [createdDate=" + createdDate + ", id=" + id + ", name=" + name + ", weight=" + weight + "]";
    }

    public EntityTest() {
    }

}
