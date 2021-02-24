package ducnguyen.springredditproject.service;


import ducnguyen.springredditproject.exceptions.SpringRedditException;
import ducnguyen.springredditproject.model.Subreddit;
import ducnguyen.springredditproject.model.SubredditSubscription;
import ducnguyen.springredditproject.model.User;
import ducnguyen.springredditproject.repository.SubredditRepository;
import ducnguyen.springredditproject.repository.SubredditSubscriptionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditSubscriptionService {
    private final SubredditSubscriptionRepository subredditSubscriptionRepository;
    private final SubredditRepository subredditRepository;

    public void subscribeToSubreddit(Long subreddit_id, User user) {
        Subreddit subreddit = getSubreddit(subreddit_id);
        SubredditSubscription subredditSubscription = new SubredditSubscription();
        subredditSubscription.setUser(user);
        subredditSubscription.setSubreddit(subreddit);

//        List<SubredditSubscription> subredditSubscriptions = user.getSubscriptions();
//        System.out.println(subredditSubscriptions);
//        for (SubredditSubscription sb2: subredditSubscriptions) {
//            System.out.println(sb2.getSubreddit().getName());
//            System.out.println(sb2.getUser().getUsername());
//        }

        subredditSubscriptionRepository.save(subredditSubscription);
    }

    @Transactional
    public void leaveSubreddit(Long subreddit_id, User user){
        System.out.println("subreddit id is " + subreddit_id);

        SubredditSubscription subredditSubscription = new SubredditSubscription();
        List<SubredditSubscription> subredditSubscriptions = user.getSubscriptions();


        for (SubredditSubscription sb2: subredditSubscriptions) {
            if(sb2.getSubreddit().getId().equals(subreddit_id)){
                subredditSubscription = sb2;
            }
        }
        System.out.println("subreddit name is " + subredditSubscription.getSubreddit().getName());
        subredditSubscriptions.remove(subredditSubscription);



//        Subreddit subreddit = getSubreddit(subreddit_id);
//        subredditSubscription.setUser(user);
//        subredditSubscription.setSubreddit(subreddit);
//
//        List<SubredditSubscription> subredditSubscriptions = subredditSubscriptionRepository.findAll();
//        System.out.println("subredditSubscriptions before remove " + subredditSubscriptions);
//        subreddit.getSubscriptions().remove(subredditSubscription);
//
//        user.getSubscriptions().remove(subredditSubscription);
//
//        subredditSubscriptions.remove(subredditSubscription);
//
//        System.out.println("subredditSubscriptions after remove " + subredditSubscriptions);

//        System.out.println("user before remove: " + user.getSubscriptions());
//        System.out.println("subreddit before remove:" + subreddit.getSubscriptions());
//
//        subredditSubscriptionRepository.delete(subredditSubscription);
//
//        user.getSubscriptions().remove(subredditSubscription);
//        subreddit.getSubscriptions().remove(subredditSubscription);
//        System.out.println("user after remove: " + user.getSubscriptions());
//        System.out.println("subreddit after remove:" + subreddit.getSubscriptions());


    }

    public Subreddit getSubreddit(Long subreddit_id){
        return subredditRepository.findById(subreddit_id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + subreddit_id));
    }

    public List<String> getAllSubredditSubscription(User user){
        List<SubredditSubscription> subredditSubscriptions = user.getSubscriptions();
        List<String> subredditSubscriptionNames = new ArrayList<>();
        for (SubredditSubscription subredditSubscription: subredditSubscriptions) {
            subredditSubscriptionNames.add(subredditSubscription.getSubreddit().getName());

            System.out.println(subredditSubscription.getSubreddit().getName());
        }
        return subredditSubscriptionNames;
    }




}
