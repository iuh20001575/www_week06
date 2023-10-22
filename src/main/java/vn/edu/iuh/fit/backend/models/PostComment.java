package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "post_comment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostComment {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private PostComment parent;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(nullable = false)
    private Boolean published;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "published_at")
    private Instant publishedAt;
    @Column(columnDefinition = "text")
    private String content;

    @OneToMany(mappedBy = "parent")
    private Set<PostComment> postComments;
}
