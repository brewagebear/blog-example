package io.brewagebear.api.mapper;


import io.brewagebear.api.dto.request.MemberRequest;
import io.brewagebear.api.dto.response.MemberResponse;
import io.github.brewagebear.global.entity.Member;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberHttpMapper {

    MemberHttpMapper INSTANCE = Mappers.getMapper(MemberHttpMapper.class);

    Member toDomain(MemberRequest request);

    MemberRequest toRequest(Member domain);

    Member toDomain(MemberResponse response);
    MemberResponse toResponse(Member domain);


    default List<MemberResponse> toListResponse(List<Member> domains){
        List<MemberResponse> list = new ArrayList<>();
        domains.forEach(d-> list.add(toResponse(d)));
        return list;
    }
}
