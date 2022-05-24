package io.brewagebear.asyncserver.domain.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.brewagebear.asyncserver.domain.user.User;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dining {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FavoriteFoodCategory foodCategory;

    @Embedded
    private Menu menu;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private User user;

    public Dining(FavoriteFoodCategory foodCategory, Menu menu, User user) {
        this.foodCategory = foodCategory;
        this.menu = menu;
        this.user = user;
    }
}
