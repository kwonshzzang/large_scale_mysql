package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.follow.service;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.follow.entity.Follow;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.follow.repository.FollowRepository;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FollowWriteService {
    private final FollowRepository followRepository;

    public Follow create(MemberDto fromMember, MemberDto toMember) {
        /**
         * 두명의 from, to 회원 정보를 받아서 저장
         * from, to validate(같은 회원이면 안됨)
         */
        Assert.isTrue(!fromMember.id().equals(toMember.id()), "From, To 회원이 동일합니다.");
        var follow = Follow.builder()
                .fromMemberId(fromMember.id())
                .toMemberId(toMember.id())
                .build();
        return followRepository.save(follow);
    }
}
