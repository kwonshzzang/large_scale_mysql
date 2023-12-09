package kr.co.kwonshzzang.largescalemysql.largescalemysql.application.controller;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.application.usecase.GetTimelinePostsUsecase;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto.DailyPostCountRequest;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto.DailyPostCountResponse;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto.PostDto;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto.RegisterPostCommand;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.entity.Post;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.service.PostReadService;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.service.PostWriteService;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.util.CursorRequest;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.util.PageCursor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostWriteService postWriteService;
    private final PostReadService postReadService;
    private final GetTimelinePostsUsecase getTimelinePostsUsecase;


    @PostMapping("")
    public PostDto register(RegisterPostCommand command) {
        return postWriteService.register(command);
    }

    @GetMapping("/daily-post-count")
    public List<DailyPostCountResponse> getDailyPostCount(DailyPostCountRequest request) {
        System.out.println(request);
        return postReadService.getDailyPostCount(request);
    }

    @GetMapping("/members/{memberId}")
    public Page<Post> getPosts(
            @PathVariable(name = "memberId") Long memberId,
            Pageable pageable
    ) {
        return postReadService.getPosts(memberId, pageable);
    }

    @GetMapping("/members/{memberId}/by-cursor")
    public PageCursor<Post> getPostsByCursor(
            @PathVariable(name = "memberId") Long memberId,
            CursorRequest cursorRequest
    ) {
        return postReadService.getPosts(memberId, cursorRequest);
    }

    @GetMapping("/members/{memberId}/timeline")
    public PageCursor<Post> getPostsTimeline(
            @PathVariable(name = "memberId") Long memberId,
            CursorRequest cursorRequest
    ) {
        return getTimelinePostsUsecase.execute(memberId, cursorRequest);
    }
}
