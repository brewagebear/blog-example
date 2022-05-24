package io.github.brewagebear.global.ports.api;

import io.github.brewagebear.global.domain.Member;
import java.util.List;
import java.util.Optional;

public interface MemberServicePort {

    List<Member> find();
    
    Optional<Member> findById(Long id);
    
    Member exist(Long id);
    
    Member create(Member Member);
    
    Member update(Long id, Member Member);
    
    void delete(Long id);
    
}
