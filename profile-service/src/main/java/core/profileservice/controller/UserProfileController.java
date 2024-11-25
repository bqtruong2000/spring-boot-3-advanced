package core.profileservice.controller;

import core.profileservice.dto.request.UserProfileCreationRequest;
import core.profileservice.dto.response.UserProfileResponse;
import core.profileservice.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    UserProfileService userProfileService;

    @PostMapping("/users/create")
    public UserProfileResponse create(@RequestBody UserProfileCreationRequest request) {
        return userProfileService.createProfile(request);
    }

    @GetMapping("/users/{profileId}")
    public UserProfileResponse get(@PathVariable String profileId) {
        return userProfileService.getProfile(profileId);
    }

}
