package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.repository;


import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.PageHelper;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto.DailyPostCountRequest;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto.DailyPostCountResponse;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
import java.time.LocalDate;

@RequiredArgsConstructor
@Repository
public class PostRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final static String  TABLE = "Post";

    private final static RowMapper<Post> POST_ROW_MAPPER = (ResultSet rs, int rowNum) ->
            Post.builder()
                    .id(rs.getLong("id"))
                    .memberId(rs.getLong("memberId"))
                    .contents(rs.getString("contents"))
                    .createdDate(rs.getObject("createdDate", LocalDate.class))
                    .createdAt(rs.getObject("createdAt", LocalDateTime.class))
                    .build();

    private final static RowMapper<DailyPostCountResponse> DAILY_POST_COUNT_ROW_MAPPER = (ResultSet rs, int rowNum) ->
            new DailyPostCountResponse(
                    rs.getLong("memberId"),
                    rs.getObject("createdDate", LocalDate.class),
                    rs.getLong("count")
            );

    public Post save(Post post) {
        if(post.getId() == null)
            return insert(post);
        throw new UnsupportedOperationException("Post는 갱신을 지원하지 않습니다.");
    }

    public void bulkInsert(List<Post> posts) {
        var sql = String.format("""
                INSERT INTO %s (memberId, contents, createdDate, createdAt)
                VALUES(:memberId, :contents, :createdDate, :createdAt)
                """, TABLE);

        SqlParameterSource[] params = posts.
                stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);
        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }

    public Page<Post> findAllByMemberId(Long memberId, Pageable pageable) {
        var sql = String.format("""
                SELECT *
                FROM %s
                WHERE memberId = :memberId
                ORDER BY %s
                LIMIT :size
                OFFSET :offset
                """, TABLE, PageHelper.orderBy(pageable.getSort()));

        var params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("size", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());

        var posts = namedParameterJdbcTemplate.query(sql, params, POST_ROW_MAPPER);

        return new PageImpl<>(posts, pageable, getCount(memberId));
    }

    public List<DailyPostCountResponse> groupByCreatedDate(DailyPostCountRequest request) {
        var sql = String.format("""
                SELECT createdDate, memberId, count(id) as count
                FROM %s
                WHERE memberId=:memberId AND createdDate BETWEEN :fromDate AND :toDate
                GROUP BY createdDate, memberId
                """, TABLE);

        var params = new BeanPropertySqlParameterSource(request);
        return namedParameterJdbcTemplate.query(sql, params, DAILY_POST_COUNT_ROW_MAPPER);
    }

    private Post insert(Post post) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(post);
        var id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Post.builder()
                .id(id)
                .memberId(post.getMemberId())
                .contents(post.getContents())
                .createdDate(post.getCreatedDate())
                .createdAt(post.getCreatedAt())
                .build();
    }

    private Long getCount(Long memberId) {
        var sql = String.format("""
                SELECT count(id)
                FROM %s
                WHERE memberId = :memberId
                """, TABLE);

        var params = new MapSqlParameterSource()
                .addValue("memberId", memberId);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);
    }

}
