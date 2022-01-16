package com.example.esa_labs24.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="DanceStudio")
@Data
public class DanceStudio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "danceStudio")
    @JsonIgnoreProperties({"danceStudio", "danceClass"})
    private List<Dancer> dancers;
}
