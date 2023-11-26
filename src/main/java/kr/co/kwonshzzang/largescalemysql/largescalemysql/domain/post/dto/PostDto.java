package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto;

import java.time.LocalDate;

public record PostDto(
        Long memberId,
        String contents,
        LocalDate createdDate
) {
}
