package io.brewagebear.asyncserver.service;

import io.brewagebear.asyncserver.domain.menu.DiningRepository;
import io.brewagebear.asyncserver.domain.user.User;
import io.brewagebear.asyncserver.domain.user.UserRepository;
import io.brewagebear.asyncserver.dto.UserMenuRequest;
import io.brewagebear.asyncserver.dto.UserMenuResponse;
import java.util.Random;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserMenuService {

    private final UserRepository userRepository;

    private final DiningRepository diningRepository;


    public UserMenuService(UserRepository userRepository, DiningRepository diningRepository) {
        this.userRepository = userRepository;
        this.diningRepository = diningRepository;
    }

    @Transactional(readOnly = true)
    public UserMenuResponse userMenu(UserMenuRequest request) throws InterruptedException {
        Thread.sleep(randomSleep());

        User user = userRepository.findBy(request.getUsername(), request.getPassword());
        return new UserMenuResponse(user.username(), diningRepository.findDiningsByUser(user));
    }

    private int randomSleep() {
        long seed = System.currentTimeMillis();
        Random rand = new Random(seed);
        return rand.nextInt(20000) + 1000;
    }

}
