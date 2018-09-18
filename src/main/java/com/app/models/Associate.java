package com.app.models;

import lombok.Builder;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Value
@Builder
@Entity
public class Associate {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
