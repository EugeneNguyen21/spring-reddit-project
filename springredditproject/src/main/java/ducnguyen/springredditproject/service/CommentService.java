package ducnguyen.springredditproject.service;


import ducnguyen.springredditproject.dto.CommentsDto;
import ducnguyen.springredditproject.exceptions.PostNotFoundException;
import ducnguyen.springredditproject.mapper.CommentMapper;
import ducnguyen.springredditproject.model.Comment;
import ducnguyen.springredditproject.model.NotificationEmail;
import ducnguyen.springredditproject.model.Post;
import ducnguyen.springredditproject.model.User;
import ducnguyen.springredditproject.repository.CommentRepository;
import ducnguyen.springredditproject.repository.PostRepository;
import ducnguyen.springredditproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class CommentService {
    private static final String POST_URL = "";
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final MailContentBuilder mailContentBuilder;
    private final CommentRepository commentRepository;
    private final MailService mailService;
    private final UserRepository userRepository;



    @Transactional
    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(authService.getCurrentUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(authService.getCurrentUser().getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(String postId){
        Post post = postRepository.findById(Long.parseLong(postId))
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
        .stream()
        .map(commentMapper::mapToDto)
        .collect(toList());
    }


    public List<CommentsDto> getAllCommentsByUserName(String username) {
        User user = userRepository.findByUsername(username).
                orElseThrow(()-> new  UsernameNotFoundException(username));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }
}
