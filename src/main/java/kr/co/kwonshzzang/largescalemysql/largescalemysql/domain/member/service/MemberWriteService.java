package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.service;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.dto.MemberDto;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.dto.RegisterMemberCommand;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.entity.Member;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.entity.MemberNicknameHistory;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.repository.MemberNicknameHistoryRepository;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@RequiredArgsConstructor
public class MemberWriteService {
    private final MemberRepository memberRepository;
    private final MemberReadService memberReadService;
    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    @Transactional
    public MemberDto register(RegisterMemberCommand command) {
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
        var savedMember = memberRepository.save(member);

        saveNicknameHistory(savedMember);

       return memberReadService.toDto(savedMember);
    }

    @Transactional
    public void changeNickname(Long memberId, String nickname) {
        /**
         * 1. 회원 이름을 변경
         * 2. 변경 내역을 저장한다.
         */
        var member = memberRepository.findById(memberId).orElseThrow();
        member.changeNickname(nickname);
        memberRepository.save(member);

        saveNicknameHistory(member);
    }

    private void saveNicknameHistory(Member member) {
        var history = MemberNicknameHistory.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();

        memberNicknameHistoryRepository.save(history);
    }
}
