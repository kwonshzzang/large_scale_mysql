package kr.co.kwonshzzang.largescalemysql.largescalemysql.application.usecase;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.service.MemberReadService;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.service.PostLikeWriteService;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.service.PostReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreatePostLikeUsecase {
    private final PostReadService postReadService;
    private final MemberReadService memberReadService;
    private final PostLikeWriteService postLikeWriteService;

    public void execute(Long postId, Long memberId) {
        var postDto = postReadService.toDto(postReadService.getPost(postId));
        var memberDto = memberReadService.getMember(memberId);
        postLikeWriteService.create(postDto, memberDto);

    }

}
