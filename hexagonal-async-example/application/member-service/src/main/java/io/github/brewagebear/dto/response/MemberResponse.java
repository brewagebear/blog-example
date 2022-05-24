package io.github.brewagebear.dto.response;

import io.github.brewagebear.global.domain.Salary;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse implements Serializable {

    private Long id;

    private String name;

    private String email;

    private LocalDate birthDate;

    private Salary salary;

}
