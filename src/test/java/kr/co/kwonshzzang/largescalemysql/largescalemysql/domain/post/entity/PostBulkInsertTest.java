package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.entity;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.repository.PostRepository;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.util.PostFixtureFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
class PostBulkInsertTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    void bulkInsert() {
        var easyRandom = PostFixtureFactory.get(3L,
                                                LocalDate.of(2023, 1, 1),
                                                LocalDate.of(2023, 12, 31));

        var creteObjectStopWatch = new StopWatch();
        creteObjectStopWatch.start();

        var posts = IntStream.range(0, 10000 * 300)
                .parallel()
                .mapToObj(p -> easyRandom.nextObject(Post.class))
                .toList();

        creteObjectStopWatch.stop();
        System.out.println("객체 생성 시간 : " + creteObjectStopWatch.getTotalTimeSeconds());


        var bulkInsertStopWatch = new StopWatch();

        bulkInsertStopWatch.start();

        postRepository.bulkInsert(posts);

        bulkInsertStopWatch.stop();
        System.out.println("벌크 Insert 시간 : " + bulkInsertStopWatch.getTotalTimeSeconds());
    }

}