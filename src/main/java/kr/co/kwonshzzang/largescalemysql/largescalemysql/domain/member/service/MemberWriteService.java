package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.service;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.dto.RegisterMemberCommand;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.entity.Member;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteService {
    private final MemberRepository memberRepository;

    public Member register(RegisterMemberCommand command) {
        /**
         * Goal - 회원정보(이메일, 닉네임, 생년월일)를 등록
         *      - 닉네임은 10자를 넘길 수 없다.
         *
         * 파라미터 - memberRegisterCommand
         * var member = Member.of(memberRegisterCommand)
         * memberRepository.save(member)
         */
        var member = Member.builder()
                .nickname(command.nickname())
                .email(command.email())
                .birthday(command.birthday())
                .build();

       return memberRepository.save(member);
    }
}
