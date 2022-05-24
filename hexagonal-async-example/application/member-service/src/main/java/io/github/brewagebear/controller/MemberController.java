package io.github.brewagebear.controller;

import static io.github.brewagebear.mapper.MemberHttpMapper.INSTANCE;

import io.github.brewagebear.dto.request.MemberRequest;
import io.github.brewagebear.dto.response.MemberResponse;
import io.github.brewagebear.global.domain.Member;
import io.github.brewagebear.global.ports.api.MemberServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberServicePort memberServicePort;


    public MemberController(MemberServicePort memberServicePort) {
        this.memberServicePort = memberServicePort;
    }

    @PostMapping
    public ResponseEntity<MemberResponse> signup(@RequestBody MemberRequest request) {

        Member member = memberServicePort.create(INSTANCE.toDomain(request));

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(INSTANCE.toResponse(member));
    }
}
