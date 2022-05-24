package io.github.brewagebear.global.entity;


import java.io.Serializable;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
public class Member implements Serializable {

    private Long id;

    private String name;

    private String password;

    private LocalDate birthDate;

    private String email;


    public Member(String name, String password, LocalDate birthDate, String email) {
        this.name = name;
        this.password = password;
        this.birthDate = birthDate;
        this.email = email;
    }
}
