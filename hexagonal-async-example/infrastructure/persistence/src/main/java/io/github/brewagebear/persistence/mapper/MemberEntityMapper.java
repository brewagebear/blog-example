package io.github.brewagebear.persistence.mapper;

import io.github.brewagebear.global.domain.Member;
import io.github.brewagebear.persistence.entity.MemberEntity;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberEntityMapper {

    MemberEntityMapper INSTANCE = Mappers.getMapper(MemberEntityMapper.class);
    Member toDomain(MemberEntity entity);
    MemberEntity toEntity(Member member);

    default List<Member> toListDomain(List<MemberEntity> entities){
        List<Member> list = new ArrayList<>();
        entities.forEach(d-> list.add(toDomain(d)));
        return list;
    }
}
