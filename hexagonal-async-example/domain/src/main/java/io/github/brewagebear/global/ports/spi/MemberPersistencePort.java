package io.github.brewagebear.global.ports.spi;

import io.github.brewagebear.global.domain.Member;
import java.util.List;
import java.util.Optional;

public interface MemberPersistencePort {

    List<Member> find();

    Optional<Member> findById(Long id);

    Member exist(Long id);

    Member create(Member Member);

    Member update(Long id, Member Member);

    void delete(Long id);

}
