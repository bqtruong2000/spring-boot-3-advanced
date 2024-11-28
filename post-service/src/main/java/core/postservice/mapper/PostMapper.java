package core.postservice.mapper;


import core.postservice.dto.request.PostRequest;
import core.postservice.dto.response.PostResponse;
import core.postservice.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponse toPostResponse(Post post);

}
