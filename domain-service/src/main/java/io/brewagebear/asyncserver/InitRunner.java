package io.brewagebear.asyncserver;

import io.brewagebear.asyncserver.domain.menu.Dining;
import io.brewagebear.asyncserver.domain.menu.DiningRepository;
import io.brewagebear.asyncserver.domain.menu.FavoriteFoodCategory;
import io.brewagebear.asyncserver.domain.menu.Menu;
import io.brewagebear.asyncserver.domain.user.User;
import io.brewagebear.asyncserver.domain.user.UserRepository;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitRunner implements CommandLineRunner {

    private final UserRepository userRepository;

    private final DiningRepository diningRepository;

    public InitRunner(UserRepository userRepository, DiningRepository diningRepository) {
        this.userRepository = userRepository;
        this.diningRepository = diningRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User bear = new User("bear", "123456");
        userRepository.save(bear);

        Menu menu1 = new Menu("짜장면", BigDecimal.valueOf(5_000));
        Menu menu2 = new Menu("탕수육", BigDecimal.valueOf(16_000));

        Dining dining1 = new Dining(FavoriteFoodCategory.CHINESE_FOOD, menu1, bear);
        Dining dining2 = new Dining(FavoriteFoodCategory.CHINESE_FOOD, menu2, bear);

        diningRepository.save(dining1);
        diningRepository.save(dining2);
    }
}
