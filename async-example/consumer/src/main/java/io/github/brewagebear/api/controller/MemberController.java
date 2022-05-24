package io.brewagebear.api.controller;

import static io.brewagebear.api.mapper.MemberHttpMapper.INSTANCE;

import io.brewagebear.api.dto.request.MemberRequest;
import io.brewagebear.api.dto.response.MemberResponse;
import io.github.brewagebear.global.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.brewagebear.global.ports.api.MemberServicePort;

@Slf4j
@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberServicePort memberServicePort;

    public MemberController(MemberServicePort memberServicePort) {
        this.memberServicePort = memberServicePort;
    }

    @PostMapping
    public ResponseEntity<MemberResponse> signup(@RequestBody MemberRequest request) {
        Member member = memberServicePort.create(INSTANCE.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(INSTANCE.toResponse(member));
    }

}
