package io.github.brewagebear.global.domain;

import java.time.LocalDate;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
public class Member {

    private Long id;

    private String name;

    private String email;

    private LocalDate birthDate;

    private Salary salary;

    @Builder
    public Member(String name, String email, LocalDate birthDate, Salary salary) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.salary = salary;
    }
}
