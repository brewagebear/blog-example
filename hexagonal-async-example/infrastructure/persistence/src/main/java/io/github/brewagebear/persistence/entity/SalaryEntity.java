package io.github.brewagebear.persistence.entity;

import java.math.BigDecimal;
import javax.persistence.Embeddable;

@Embeddable
public class SalaryEntity {

    private BigDecimal amount;

}
