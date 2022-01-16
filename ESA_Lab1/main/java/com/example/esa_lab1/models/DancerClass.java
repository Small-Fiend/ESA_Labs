package com.example.esa_lab1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="DancerClass")
@Data
public class DancerClass implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "style")
    private String style;

    @Column(name = "trainer")
    private String trainer;

    @OneToMany(mappedBy = "dancerClass", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"dancerClass", "dancerStudio"})
    private List<Dancer> dancers;
}
