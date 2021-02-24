package ducnguyen.springredditproject.repository;


import ducnguyen.springredditproject.model.Comment;
import ducnguyen.springredditproject.model.Post;
import ducnguyen.springredditproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
