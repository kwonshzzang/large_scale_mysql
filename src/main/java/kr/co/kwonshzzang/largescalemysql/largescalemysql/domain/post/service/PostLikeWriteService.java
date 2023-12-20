package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.service;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.dto.MemberDto;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto.PostDto;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.entity.PostLike;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.repository.PostLikeRepository;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeWriteService {
    private final PostLikeRepository postLikeRepository;

    public Long create(PostDto postDto, MemberDto memberDto) {
        var postlike = PostLike.builder()
                .postId(postDto.id())
                .memberId(memberDto.id())
                .build();
        return postLikeRepository.save(postlike).getPostId();
    }
}
