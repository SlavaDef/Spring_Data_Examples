package com.example.springdataexample.data.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
@Table(name = "\"user\"")
@Data
@Entity
public class User {

    @Id
    private String email;

    private String fullName;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
