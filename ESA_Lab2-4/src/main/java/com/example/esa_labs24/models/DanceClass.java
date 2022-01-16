package com.example.esa_labs24.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="DanceClass")
@Data
public class DanceClass implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "style")
    private String style;

    @Column(name = "trainer")
    private String trainer;

    @OneToMany(mappedBy = "danceClass", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"danceClass", "danceStudio"})
    private List<Dancer> dancers;
}
