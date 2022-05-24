package global.service;

import global.entity.Member;
import java.util.List;
import java.util.Optional;
import ports.api.MemberServicePort;
import ports.rdb.MemberRdbPort;

public class MemberService implements MemberServicePort {

    private final MemberRdbPort memberRdbPort;

    public MemberService(MemberRdbPort memberRdbPort) {
        this.memberRdbPort = memberRdbPort;
    }

    @Override
    public List<Member> find() {
        return memberRdbPort.find();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberRdbPort.findById(id);
    }

    @Override
    public Member exist(Long id) {
        return memberRdbPort.exist(id);
    }

    @Override
    public Member create(Member member) {
        return memberRdbPort.create(member);
    }

    @Override
    public Member update(Long id, Member member) {
        return memberRdbPort.update(id, member);
    }

    @Override
    public void delete(Long id) {
        memberRdbPort.delete(id);
    }
}
