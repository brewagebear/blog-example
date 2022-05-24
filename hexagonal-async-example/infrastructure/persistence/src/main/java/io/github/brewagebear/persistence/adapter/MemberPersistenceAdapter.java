package io.github.brewagebear.persistence.adapter;

import static io.github.brewagebear.persistence.mapper.MemberEntityMapper.INSTANCE;

import io.github.brewagebear.global.domain.Member;
import io.github.brewagebear.global.ports.spi.MemberPersistencePort;
import io.github.brewagebear.persistence.entity.MemberEntity;
import io.github.brewagebear.persistence.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Primary
@Component
public class MemberPersistenceAdapter implements MemberPersistencePort {

    private final MemberRepository repository;

    public MemberPersistenceAdapter(MemberRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> find() {
        return INSTANCE.toListDomain(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findById(Long id) {
        return Optional.of(exist(id));
    }

    @Override
    @Transactional
    public Member exist(Long id) {
        MemberEntity entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

        return INSTANCE.toDomain(entity);
    }

    @Override
    @Transactional
    public Member create(Member member) {
        return INSTANCE.toDomain(repository.save(INSTANCE.toEntity(member)));
    }

    @Override
    @Transactional
    public Member update(Long id, Member member) {
        Member savedMember = exist(id);
        BeanUtils.copyProperties(member, savedMember, "id");

        return INSTANCE.toDomain(repository.save(INSTANCE.toEntity(savedMember)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Member savedMember = exist(id);
        repository.delete(INSTANCE.toEntity(savedMember));
    }
}
