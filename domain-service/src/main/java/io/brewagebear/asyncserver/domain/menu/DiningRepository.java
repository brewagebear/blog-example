package io.brewagebear.asyncserver.domain.menu;

import io.brewagebear.asyncserver.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiningRepository extends JpaRepository<Dining, Long> {

    List<Dining> findDiningsByUser(User user);
}
