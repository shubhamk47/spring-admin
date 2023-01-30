package com.springadmin.springadmin.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

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
    @JoinColumn(name = "entityTest")
    EntityNew entityNew;

    public EntityTest() {
    }

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

}
