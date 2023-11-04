package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.backend.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByPublished(boolean published, Pageable pageable);
}