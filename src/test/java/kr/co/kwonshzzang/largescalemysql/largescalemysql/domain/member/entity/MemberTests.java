package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.entity;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.util.MemberFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

class MemberTests {

    @Test
    @DisplayName("회원의 닉네임을 변경할 수 있다.")
    void testChangeName() {
//        LongStream.range(1, 10)
//                .mapToObj(MemberFixtureFactory::create)
//                .forEach(member -> {
//                    System.out.println(member.getNickname());
//                });
        var member = MemberFixtureFactory.create();
        var expected = "pnu";

        member.changeNickname(expected);
        assertEquals(expected, member.getNickname());
    }

    @Test
    @DisplayName("회원의 닉네임은 10자를 초과할 수 없다.")
    void testNicknameMaxLength() {
        var member = MemberFixtureFactory.create();
        var overMaxLengthName = "pnupnupnupnupnu";

        assertThrows(IllegalArgumentException.class, () -> member.changeNickname(overMaxLengthName) );
    }



}