package kr.co.kwonshzzang.largescalemysql.largescalemysql.application.controller;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.application.usecase.CreatePostLikeUsecase;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.application.usecase.CreatePostUsecase;
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
    private final CreatePostUsecase createPostUsecase;
    private final CreatePostLikeUsecase createPostLikeUsecase;


    @PostMapping("")
    public PostDto register(RegisterPostCommand command) {
        return createPostUsecase.execute(command);
    }

    @GetMapping("/daily-post-count")
    public List<DailyPostCountResponse> getDailyPostCount(DailyPostCountRequest request) {
        System.out.println(request);
        return postReadService.getDailyPostCount(request);
    }

    @GetMapping("/members/{memberId}")
    public Page<PostDto> getPosts(
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
        return getTimelinePostsUsecase.executeByTimeline(memberId, cursorRequest);
    }

    @PutMapping("/{id}/like/v1")
    public void likePostV1(@PathVariable(name = "id") Long id) {
      //  postWriteService.likePost(id); //Positive Lock
        postWriteService.likePostByOptimisticLock(id); //Optimistic Lock
    }

    @PutMapping("/{id}/like/v2")
    public void likePostV2(@PathVariable(name = "id") Long id, @RequestParam(name ="memberId") Long memberId) {
        //  postWriteService.likePost(id); //Positive Lock
        createPostLikeUsecase.execute(id, memberId); //Optimistic Lock
    }


}
