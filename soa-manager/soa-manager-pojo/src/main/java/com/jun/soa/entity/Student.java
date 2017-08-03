package com.jun.soa.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="t_student")
public class Student implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 20)
    private String name;

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    public Student(Long id,String name){
        this.id=id;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
