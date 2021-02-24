package ducnguyen.springredditproject.controller;


import ducnguyen.springredditproject.model.User;
import ducnguyen.springredditproject.service.AuthService;
import ducnguyen.springredditproject.service.SubredditSubscriptionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subredditSubscription")
@AllArgsConstructor
@Slf4j
public class SubredditSubscriptionController {

    private final SubredditSubscriptionService subredditSubscriptionService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<List<String>> getAllSubredditSubscriptions() {
        User user = authService.getCurrentUser();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditSubscriptionService.getAllSubredditSubscription(user));
    }
}
