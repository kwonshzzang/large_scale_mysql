package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record DailyPostCountRequest(
        Long memberId,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate fromDate,
        @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
        LocalDate toDate
) {
}
