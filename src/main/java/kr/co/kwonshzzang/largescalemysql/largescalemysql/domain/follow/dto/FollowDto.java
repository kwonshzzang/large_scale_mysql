package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.follow.dto;

import java.time.LocalDateTime;

public record FollowDto(
         Long id,
         Long fromMemberId,
         Long toMemberId,
         LocalDateTime createdAt
) {
}
