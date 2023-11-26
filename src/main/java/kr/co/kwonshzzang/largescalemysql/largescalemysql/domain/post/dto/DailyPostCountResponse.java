package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto;

import java.time.LocalDate;

public record DailyPostCountResponse(
        Long memberId,
        LocalDate createdDate,
        Long count
) {
}
