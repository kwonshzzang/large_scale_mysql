package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.repository;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.entity.MemberNicknameHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberNicknameHistoryRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String TABLE = "MemberNicknameHistory";
    private final static RowMapper<MemberNicknameHistory> MEMBER_NICKNAME_HISTORY_ROW_MAPPER = (ResultSet rs, int rowNum) -> MemberNicknameHistory
            .builder()
            .id(rs.getLong("id"))
            .memberId(rs.getLong("memberId"))
            .nickname(rs.getString("nickname"))
            .createdAt(rs.getObject("createdAt", LocalDateTime.class))
            .build();

    public MemberNicknameHistory save(MemberNicknameHistory memberNicknameHistory) {
        if(memberNicknameHistory.getId() == null) {
            return insert(memberNicknameHistory);
        }
        throw new UnsupportedOperationException();
    }

    private MemberNicknameHistory insert(MemberNicknameHistory memberNicknameHistory) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(memberNicknameHistory);
        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return MemberNicknameHistory
                .builder()
                .id(memberNicknameHistory.getId())
                .memberId(memberNicknameHistory.getMemberId())
                .nickname(memberNicknameHistory.getNickname())
                .createdAt(memberNicknameHistory.getCreatedAt())
                .build();
    }

    public List<MemberNicknameHistory> findAllByMemberId(Long memberId) {
        var  sql = String.format("SELECT * FROM %s WHERE memberId = :memberId", TABLE);
        var param = new MapSqlParameterSource().addValue("memberId", memberId);
        return namedParameterJdbcTemplate.query(sql, param, MEMBER_NICKNAME_HISTORY_ROW_MAPPER);
    }


}
