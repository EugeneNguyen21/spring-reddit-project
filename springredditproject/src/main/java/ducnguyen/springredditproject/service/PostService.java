package ducnguyen.springredditproject.service;



import ducnguyen.springredditproject.dto.PostRequest;
import ducnguyen.springredditproject.dto.PostResponse;
import ducnguyen.springredditproject.exceptions.SpringRedditException;
import ducnguyen.springredditproject.exceptions.SubredditNotFoundException;
import ducnguyen.springredditproject.mapper.PostMapper;
import ducnguyen.springredditproject.model.Post;
import ducnguyen.springredditproject.model.Subreddit;
import ducnguyen.springredditproject.model.User;
import ducnguyen.springredditproject.repository.PostRepository;
import ducnguyen.springredditproject.repository.SubredditRepository;
import ducnguyen.springredditproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostRequest save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        User currentUser = authService.getCurrentUser();

        postRepository.save(postMapper.map(postRequest, subreddit, currentUser));
        return postRequest;
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No post found with ID - " + id));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId){
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));

        return postRepository.findAllBySubreddit(subreddit)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        Long user_id = user.getUserId();
        return postRepository.findByUser_UserId(user_id)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }


    public void uploadPostImage(String postId, MultipartFile file) {
    }
}
