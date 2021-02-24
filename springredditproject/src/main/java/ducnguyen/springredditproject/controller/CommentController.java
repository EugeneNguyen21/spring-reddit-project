package ducnguyen.springredditproject.controller;


import ducnguyen.springredditproject.dto.CommentsDto;
import ducnguyen.springredditproject.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto) {
        commentService.save(commentsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-postId/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPosts(@PathVariable String postId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsByUsername(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsByUserName(username));
    }
}
