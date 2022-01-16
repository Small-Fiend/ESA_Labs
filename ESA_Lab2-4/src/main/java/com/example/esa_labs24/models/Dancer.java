package com.example.esa_labs24.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Dancer")
@Data
public class Dancer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @ManyToOne()
    @JoinColumn(name="classId")
    @JsonIgnoreProperties({"dancers"})
    private DanceClass danceClass;

    @ManyToOne()
    @JoinColumn(name="studioId")
    @JsonIgnoreProperties({"dancers"})
    private DanceStudio danceStudio;
}
