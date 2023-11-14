package kr.co.kwonshzzang.largescalemysql.largescalemysql.application.usecase;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.follow.dto.FollowDto;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.follow.service.FollowReadService;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.follow.service.FollowWriteService;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateFollowMemberUsecase {
    private final MemberReadService memberReadService;
    private final FollowWriteService followWriteService;
    private final FollowReadService followReadService;

    public FollowDto execute(Long fromMemberId, Long toMemberId) {
        /**
         * 1. 입력받은 MemberId로 회원 조회
         * 2. FollowWriteService.create() 호출
         */
        var fromMember = memberReadService.getMember(fromMemberId);
        var toMember = memberReadService.getMember(toMemberId);
        var follow = followWriteService.create(fromMember, toMember);
        return followReadService.toDto(follow);
    }
}
