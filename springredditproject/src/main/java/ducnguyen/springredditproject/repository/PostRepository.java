package ducnguyen.springredditproject.repository;


import ducnguyen.springredditproject.model.Post;
import ducnguyen.springredditproject.model.Subreddit;
import ducnguyen.springredditproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);

    List<Post> findByUser_UserId(Long user_id);

    Optional<Post> findById(Long postId);

    Long getSubredditIdByPostName(Long postId);
}
