package kr.co.kwonshzzang.largescalemysql.largescalemysql.application.usecase;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.follow.service.FollowReadService;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.dto.MemberDto;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetFollowingMemberUsecase {
    private final FollowReadService followReadService;
    private final MemberReadService memberReadService;

    public List<MemberDto> execute(Long memberId) {
        /**
         * 1. fromMemberId = memberId -> FollowList
         * 2. 1번을 순회하면서 회원정보를 찾는다.
         */
        var followings = followReadService.getFollowings(memberId);
        return followings.stream().map(followDto -> memberReadService.getMember(followDto.toMemberId())).toList();
    }
}
