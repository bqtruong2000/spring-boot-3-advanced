package core.profileservice.controller;

import core.profileservice.dto.request.UserProfileCreationRequest;
import core.profileservice.dto.response.UserProfileResponse;
import core.profileservice.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    UserProfileService userProfileService;

    @GetMapping("/users/{profileId}")
    public UserProfileResponse get(@PathVariable String profileId) {
        return userProfileService.getProfile(profileId);
    }

    @GetMapping("/users/all")
    public List<UserProfileResponse> getAll() {
        return userProfileService.getAllProfiles();
    }

}
