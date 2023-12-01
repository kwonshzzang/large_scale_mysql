package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.service;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto.DailyPostCountRequest;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto.DailyPostCountResponse;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto.PostDto;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.entity.Post;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostReadService {
    private final PostRepository postRepository;

    public List<DailyPostCountResponse> getDailyPostCount(DailyPostCountRequest request) {
        /**
         * 반환값 -> 리스트(작성회원, 작성일자, 작성 게시물 갯수)
         * SELECT createdDate, memberId, count(id)
         * FROM post
         * WHERE memberId=:memberId AND createdDate BETWEEN :fromDate AND :toDate
         * GROUP BY createdDate, memberId
         **/
        return postRepository.groupByCreatedDate(request);
    }

    public Page<Post> getPosts(Long memberId, Pageable pageable) {
        return postRepository.findAllByMemberId(memberId, pageable);
    }



    public PostDto toDto(Post post) {
        return new PostDto(post.getMemberId(), post.getContents(), post.getCreatedDate());
    }

}