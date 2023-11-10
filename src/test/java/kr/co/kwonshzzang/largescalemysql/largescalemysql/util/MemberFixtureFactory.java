package kr.co.kwonshzzang.largescalemysql.largescalemysql.util;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.entity.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

/**
 * ObjectMother Pattern
 */
public class MemberFixtureFactory {
    public static Member create() {
        var param = new EasyRandomParameters();
        return new EasyRandom(param).nextObject(Member.class);
    }

    public static Member create(Long seed) {
        var param = new EasyRandomParameters().seed(seed);
        return new EasyRandom(param).nextObject(Member.class);
    }
}
