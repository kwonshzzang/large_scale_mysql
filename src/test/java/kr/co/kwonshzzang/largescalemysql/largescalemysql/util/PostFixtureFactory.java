package kr.co.kwonshzzang.largescalemysql.largescalemysql.util;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.entity.Post;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.time.LocalDate;

import static org.jeasy.random.FieldPredicates.*;


public class PostFixtureFactory {

    public static EasyRandom get(Long memberId, LocalDate fromDate, LocalDate toDate) {
        var idPredicate = named("id")
                .and(ofType(Long.class))
                .and(inClass(Post.class));

        var memberIdPredicate = named("memberId")
                .and(ofType(Long.class))
                .and(inClass(Post.class));

        var params = new EasyRandomParameters()
                .excludeField(idPredicate)
                .dateRange(fromDate, toDate)
                .randomize(memberIdPredicate, () -> memberId);

        return new EasyRandom(params);
    }
}
