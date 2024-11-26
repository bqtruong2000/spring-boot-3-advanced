package core.profileservice.mapper;

import core.profileservice.dto.request.UserProfileCreationRequest;
import core.profileservice.dto.response.UserProfileResponse;
import core.profileservice.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(UserProfileCreationRequest request);

    UserProfileResponse toUserProfileResponse(UserProfile entity);
}
