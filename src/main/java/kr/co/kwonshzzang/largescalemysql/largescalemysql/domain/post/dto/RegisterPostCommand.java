package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto;

public record RegisterPostCommand(
        Long memberId,
        String contents
) {
}
