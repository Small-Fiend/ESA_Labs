package com.example.esa_lab1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="DancerStudio")
@Data
public class DancerStudio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "dancerStudio")
    @JsonIgnoreProperties({"dancerStudio", "dancerClass"})
    private List<Dancer> dancers;
}
