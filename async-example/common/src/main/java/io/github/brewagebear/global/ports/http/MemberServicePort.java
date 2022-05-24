package io.github.brewagebear.global.ports.api;

import io.github.brewagebear.global.entity.Member;
import java.util.List;
import java.util.Optional;

public interface MemberServicePort {

    List<Member> find();

    Optional<Member> findById(Long id);

    Member exist(Long id);

    Member create(Member member);

    Member update(Long id, Member member);

    void delete(Long id);

}
