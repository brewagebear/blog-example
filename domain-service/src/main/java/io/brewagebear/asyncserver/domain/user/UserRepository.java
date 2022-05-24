package io.brewagebear.asyncserver.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsernameAndPassword(String username, String password);

    default User findBy(String username, String password) {
        return findUserByUsernameAndPassword(username, password)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
    }
}
