package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.follow.service;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.follow.dto.FollowDto;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.follow.entity.Follow;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowReadService {
    private final FollowRepository followRepository;

    public List<FollowDto> getFollowings(Long memberId) {
       return followRepository.findAllByFromMemberId(memberId).stream().map(this::toDto).toList();
    }

    public List<FollowDto> getFollowers(Long memberId) {
        return followRepository.findAllByToMemberId(memberId).stream().map(this::toDto).toList();
    }

    public FollowDto toDto(Follow follow) {
        return  new FollowDto(follow.getId(), follow.getFromMemberId(), follow.getToMemberId(), follow.getCreatedAt());
    }
}
