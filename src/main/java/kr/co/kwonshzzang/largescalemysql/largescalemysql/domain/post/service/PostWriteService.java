package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.service;

import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto.PostDto;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.dto.RegisterPostCommand;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.entity.Post;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostWriteService {
    private final PostRepository postRepository;
    private final PostReadService postReadService;

    public PostDto register(RegisterPostCommand command) {
        var post = Post.builder()
                .memberId(command.memberId())
                .contents(command.contents())
                .build();
        var savedPost = postRepository.save(post);

        return postReadService.toDto(savedPost);
    }

    @Transactional
    public void likePost(Long id) {
        var post = postRepository.findById(id, true).orElseThrow(RuntimeException::new);
        post.incrementLikeCount();
        postRepository.save(post);
    }
}
