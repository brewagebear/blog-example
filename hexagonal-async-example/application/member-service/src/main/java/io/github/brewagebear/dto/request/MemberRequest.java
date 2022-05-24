package io.github.brewagebear.dto.request;

import io.github.brewagebear.global.domain.Salary;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest {

    private String name;

    private String email;

    private LocalDate birthDate;

    private Salary salary;

}
