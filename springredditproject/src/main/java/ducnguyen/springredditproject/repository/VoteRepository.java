package ducnguyen.springredditproject.repository;


import ducnguyen.springredditproject.model.Post;
import ducnguyen.springredditproject.model.User;
import ducnguyen.springredditproject.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}
