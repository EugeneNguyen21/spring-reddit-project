package ducnguyen.springredditproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Subreddit {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotBlank(message = "Community name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @OneToMany(fetch = LAZY)
    private List<Post> posts;
    private Instant createdDate;

    @OneToMany(mappedBy = "subreddit", orphanRemoval = true, cascade = CascadeType.PERSIST)
    List<SubredditSubscription> subscriptions;

    public List<SubredditSubscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<SubredditSubscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}