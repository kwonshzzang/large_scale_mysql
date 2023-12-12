package kr.co.kwonshzzang.largescalemysql.largescalemysql.application.usecase;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.follow.dto.FollowDto;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.follow.service.FollowReadService;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.entity.Post;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.entity.Timeline;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.service.PostReadService;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.service.TimelineReadService;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.util.CursorRequest;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTimelinePostsUsecase {
    private final FollowReadService followReadService;
    private final TimelineReadService timelineReadService;
    private final PostReadService postReadService;

    public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest) {
        /**
         * 1. memberId -> follow 조회
         * 2. 1번의 결과로 계시물 조회
         */
        var followings = followReadService.getFollowings(memberId);
        var followingMemberIds = followings.stream().map(FollowDto::toMemberId).toList();
        return postReadService.getPosts(followingMemberIds, cursorRequest);
    }

    public PageCursor<Post> executeByTimeline(Long memberId, CursorRequest cursorRequest) {
        /**
         * 1. Timeline 테이블을 조회
         * 2. 1번에 해당하는 게시물을 조회
         */
        var pageTimelines = timelineReadService.getTimelines(memberId, cursorRequest);
        var postIds = pageTimelines.body().stream().map(Timeline::getPostId).toList();
        var posts = postReadService.getPosts(postIds);

        return new PageCursor<>(pageTimelines.nextCursorRequest(),  posts);

    }
}
