package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto;

import java.time.LocalDate;

public record PostDto(
        Long id,
        Long memberId,
        String contents,
        Long likeCount,
        LocalDate createdDate
) {
}
