package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.service;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.dto.MemberDto;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.dto.MemberNicknameHistoryDto;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.entity.Member;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.entity.MemberNicknameHistory;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.repository.MemberNicknameHistoryRepository;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberReadService {
    private final MemberRepository memberRepository;
    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public MemberDto getMember(Long id) {
        return toDto(memberRepository.findById(id).orElseThrow());
    }

    public List<MemberNicknameHistoryDto> getNicknameHistories(Long memberId) {
        return memberNicknameHistoryRepository.findAllByMemberId(memberId).stream().map(this::toDto).toList();
    }

    public MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getEmail(), member.getNickname(), member.getBirthday());
    }

    public MemberNicknameHistoryDto toDto(MemberNicknameHistory history) {
        return new MemberNicknameHistoryDto(history.getId(), history.getMemberId(), history.getNickname(), history.getCreatedAt());
    }
}
