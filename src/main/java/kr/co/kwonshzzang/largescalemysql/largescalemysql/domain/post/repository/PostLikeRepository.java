package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.repository;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.entity.PostLike;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.entity.Timeline;
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

@RequiredArgsConstructor
@Repository
public class PostLikeRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final static String  TABLE = "PostLike";

    private final static RowMapper<PostLike> POSTLIKE_ROW_MAPPER = (ResultSet rs, int rowNum) ->
            PostLike.builder()
                    .id(rs.getLong("id"))
                    .memberId(rs.getLong("memberId"))
                    .postId(rs.getLong("postId"))
                    .createdAt(rs.getObject("createdAt", LocalDateTime.class))
                    .build();

    public PostLike save(PostLike postLike) {
        if(postLike.getId() == null)
            return insert(postLike);
        throw new UnsupportedOperationException("PostLike은 갱신을 지원하지 않습니다.");
    }


    private PostLike insert(PostLike postLike) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(postLike);
        var id = jdbcInsert.executeAndReturnKey(params).longValue();

        return PostLike.builder()
                .id(id)
                .memberId(postLike.getMemberId())
                .postId(postLike.getPostId())
                .createdAt(postLike.getCreatedAt())
                .build();
    }

    public Long getCount(Long postId) {
        var sql = String.format("""
                SELECT count(id)
                FROM %s
                WHERE postId = :postId
                """, TABLE);

        var params = new MapSqlParameterSource()
                .addValue("postId", postId);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);
    }
}
