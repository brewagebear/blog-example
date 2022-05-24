package io.github.brewagebear.global.service;

import io.github.brewagebear.global.domain.Member;
import io.github.brewagebear.global.ports.api.MemberServicePort;
import io.github.brewagebear.global.ports.spi.MemberPersistencePort;
import java.util.List;
import java.util.Optional;

public class MemberService implements MemberServicePort {

    private final MemberPersistencePort memberPersistencePort;

    public MemberService(MemberPersistencePort memberPersistencePort) {
        this.memberPersistencePort = memberPersistencePort;
    }

    @Override
    public List<Member> find() {
        return memberPersistencePort.find();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberPersistencePort.findById(id);
    }

    @Override
    public Member exist(Long id) {
        return memberPersistencePort.exist(id);
    }

    @Override
    public Member create(Member member) {
        return memberPersistencePort.create(member);
    }

    @Override
    public Member update(Long id, Member member) {
        return memberPersistencePort.update(id, member);
    }

    @Override
    public void delete(Long id) {
        memberPersistencePort.delete(id);
    }
}
