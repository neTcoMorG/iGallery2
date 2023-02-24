package com.igrallery.jun.domain.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "MEMBER")
public class User {

    protected User () {}
    public User(String name, String email, String thumbnail) {
        this.name = name;
        this.email = email;
        this.thumbnail = thumbnail;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String name;
    private String email;
    private String thumbnail;

    @OneToMany(mappedBy = "user") private List<Gallery> galleries = new ArrayList<>();

}
