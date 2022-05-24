package io.brewagebear.asyncserver.domain.menu;

import java.math.BigDecimal;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    private String title;

    private BigDecimal price;

    public Menu(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Menu{" +
            "title='" + title + '\'' +
            ", price=" + price +
            '}';
    }
}
