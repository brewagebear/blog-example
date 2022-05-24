package io.github.brewagebear;

import io.github.brewagebear.global.ports.api.MemberServicePort;
import io.github.brewagebear.global.ports.spi.MemberPersistencePort;
import io.github.brewagebear.global.service.MemberService;
import io.github.brewagebear.persistence.adapter.MemberPersistenceAdapter;
import io.github.brewagebear.persistence.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseBeanConfiguration {
    @Bean
    public MemberPersistencePort memberPersistencePort(MemberRepository repository) {
        return new MemberPersistenceAdapter(repository);
    }

    @Bean
    public MemberServicePort memberServicePort(MemberPersistencePort memberPersistencePort) {
        return new MemberService(memberPersistencePort);
    }
}
