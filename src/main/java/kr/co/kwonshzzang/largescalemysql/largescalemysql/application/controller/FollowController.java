package kr.co.kwonshzzang.largescalemysql.largescalemysql.application.controller;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.application.usecase.CreateFollowMemberUsecase;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.application.usecase.GetFollowingMemberUsecase;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.follow.dto.FollowDto;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.dto.MemberDto;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {
    private final CreateFollowMemberUsecase createFollowMemberUsecase;
    private final GetFollowingMemberUsecase getFollowingMemberUsecase;

    @PostMapping("/{fromMemberId}/{toMemberId}")
    public FollowDto create(@PathVariable(name = "fromMemberId") Long fromMemberId,
                             @PathVariable(name = "toMemberId") Long toMemberId) {
        return createFollowMemberUsecase.execute(fromMemberId, toMemberId);
    }

    @GetMapping("/members/{memberId}")
    public List<MemberDto> getFollowings(@PathVariable(name = "memberId") Long memberId) {
        return getFollowingMemberUsecase.execute(memberId);
    }
}

