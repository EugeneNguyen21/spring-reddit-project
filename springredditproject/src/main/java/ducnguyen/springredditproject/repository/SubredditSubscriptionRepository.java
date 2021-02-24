package ducnguyen.springredditproject.repository;


import ducnguyen.springredditproject.model.SubredditSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubredditSubscriptionRepository extends JpaRepository<SubredditSubscription, Long> {

}
