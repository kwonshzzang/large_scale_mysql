package kr.co.kwonshzzang.largescalemysql.largescalemysql.application.usecase;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.follow.dto.FollowDto;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.follow.service.FollowReadService;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto.PostDto;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto.RegisterPostCommand;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.entity.Timeline;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.service.PostWriteService;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.service.TimelineWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class CreatePostUsecase {
    private final PostWriteService postWriteService;
    private final FollowReadService followReadService;
    private final TimelineWriteService timelineWriteService;

    public PostDto execute(RegisterPostCommand command) {
        var postDto = postWriteService.register(command);
        var followerIds = followReadService.getFollowers(command.memberId())
                .stream()
                .map(FollowDto::fromMemberId).toList();

        timelineWriteService.deliveryToTimeline(postDto.id(), followerIds);
        return postDto;
    }


}
