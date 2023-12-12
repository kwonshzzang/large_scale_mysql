package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.service;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.entity.Timeline;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.repository.TimelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TimelineWriteService {
    private final TimelineRepository timelineRepository;

    public void deliveryToTimeline(Long postId, List<Long> fromMemberIds) {
        var timeLines = fromMemberIds.stream()
                .map(fromMemberId -> Timeline.builder().memberId(fromMemberId).postId(postId).build())
                .toList();

        timelineRepository.bulkInsert(timeLines);
    }
}
