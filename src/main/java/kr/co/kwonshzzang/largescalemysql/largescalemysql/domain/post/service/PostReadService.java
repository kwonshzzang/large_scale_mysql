package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.service;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto.DailyPostCountRequest;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto.DailyPostCountResponse;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto.PostDto;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.entity.Post;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.repository.PostLikeRepository;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.repository.PostRepository;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.util.CursorRequest;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostReadService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

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

    public Post getPost(Long postId) {
        return postRepository.findById(postId, false).orElseThrow(RuntimeException::new);
    }

    public Page<PostDto> getPosts(Long memberId, Pageable pageable) {
        return postRepository.findAllByMemberId(memberId, pageable).map(this::toDto);
    }

    public PageCursor<Post> getPosts(Long memberId, CursorRequest cursorRequest) {
        List<Post> posts = findAllBy(memberId, cursorRequest);

        var nextKey = getNextKey(posts);

        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    public PageCursor<Post> getPosts(List<Long> memberIds, CursorRequest cursorRequest) {
        List<Post> posts = findAllBy(memberIds, cursorRequest);

        var nextKey = getNextKey(posts);

        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    public List<Post> getPosts(List<Long> ids) {
        return postRepository.findAllByIds(ids);
    }

    public PostDto toDto(Post post) {
        return new PostDto(post.getId(), post.getMemberId(), post.getContents(), postLikeRepository.getCount(post.getId()), post.getVersion(), post.getCreatedDate(), post.getCreatedAt());
    }


    private List<Post> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if(cursorRequest.hasKey()) {
           return postRepository.findAllByMemberLessThanIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size());
        } else {
           return postRepository.findAllByMemberIdAndOrderByIdDesc(memberId, cursorRequest.size());
        }
    }

    private List<Post> findAllBy(List<Long> memberIds, CursorRequest cursorRequest) {
        if(cursorRequest.hasKey()) {
            return postRepository.findAllByInMemberLessThanIdsAndOrderByIdDesc(cursorRequest.key(), memberIds, cursorRequest.size());
        } else {
            return postRepository.findAllByInMemberIdsAndOrderByIdDesc(memberIds, cursorRequest.size());
        }
    }

    private Long getNextKey(List<Post> posts) {
        return posts.stream()
                .mapToLong(Post::getId).min().orElse(CursorRequest.NONE_KEY);
    }


}