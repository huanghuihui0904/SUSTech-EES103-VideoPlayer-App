package com.example.db_proj2_test1.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Video {
    @Id
    private Integer id;
    private String link;


}