package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "post")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Post parent;
    @Column(length = 75, nullable = false)
    private String title;
    @Column(name = "meta_title", length = 100)
    private String metaTitle;
    @Column(columnDefinition = "text")
    private String summary;
    @Column(nullable = false)
    private Boolean published;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at")
    private Instant updatedAt;
    @Column(name = "published_at")
    private Instant publishedAt;
    @Column(columnDefinition = "text")
    private String content;

    @OneToMany(mappedBy = "post")
    private Set<PostComment> postComments;
    @OneToMany(mappedBy = "parent")
    private Set<Post> posts;

    public Post(Long id) {
        this.id = id;
    }

    public Post(User author, Post parent, String title, String metaTitle, String summary, Boolean published, Instant createdAt, Instant updatedAt, Instant publishedAt, String content) {
        this.author = author;
        this.parent = parent;
        this.title = title;
        this.metaTitle = metaTitle;
        this.summary = summary;
        this.published = published;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.publishedAt = publishedAt;
        this.content = content;
    }
}
