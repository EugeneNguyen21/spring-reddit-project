package ducnguyen.springredditproject.controller;


import ducnguyen.springredditproject.dto.SubredditDto;
import ducnguyen.springredditproject.model.User;
import ducnguyen.springredditproject.service.AuthService;
import ducnguyen.springredditproject.service.SubredditService;
import ducnguyen.springredditproject.service.SubredditSubscriptionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;
    private final SubredditSubscriptionService subredditSubscriptionService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) {
        if(!subredditService.isSubredditExisted(subredditDto.getName())){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(subredditService.save(subredditDto));
        }
        subredditDto.setMessage("this subreddit name already exists. Please try another name");
        return ResponseEntity.status(HttpStatus.OK)
                .body(subredditDto);
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.getSubreddit(id));
    }

    @PostMapping("/subscribeSubreddit/{subreddit_id}")
    public ResponseEntity<String> subscribeSubreddit(@PathVariable Long subreddit_id){
        User user = authService.getCurrentUser();
        subredditSubscriptionService.subscribeToSubreddit(subreddit_id, user);
        return new ResponseEntity<>("subscribed Successfully", OK);
    }

    @PostMapping("/leaveSubreddit/{subreddit_id}")
    public ResponseEntity<String> leaveSubreddit(@PathVariable Long subreddit_id){
        User user = authService.getCurrentUser();
        subredditSubscriptionService.leaveSubreddit(subreddit_id, user);
        return new ResponseEntity<>("leave subreddit Successfully", OK);
    }



}
