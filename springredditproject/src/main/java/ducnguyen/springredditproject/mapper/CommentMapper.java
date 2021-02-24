package ducnguyen.springredditproject.mapper;

import ducnguyen.springredditproject.dto.CommentsDto;

import ducnguyen.springredditproject.model.Comment;
import ducnguyen.springredditproject.model.Post;
import ducnguyen.springredditproject.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment map(CommentsDto commentsDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentsDto mapToDto(Comment comment);

}
