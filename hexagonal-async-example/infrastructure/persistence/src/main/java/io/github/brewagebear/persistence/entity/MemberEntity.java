package io.github.brewagebear.persistence.entity;

import java.time.LocalDate;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MemberEntity {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private LocalDate birthDate;

    @Embedded
    private SalaryEntity salaryEntity;

    @Builder
    public MemberEntity(String name, String email, LocalDate birthDate, SalaryEntity salaryEntity) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.salaryEntity = salaryEntity;
    }
}
