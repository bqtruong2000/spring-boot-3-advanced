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
public class InternalUserProfileController {
    UserProfileService userProfileService;

    @PostMapping("/internal/users/create")
    public UserProfileResponse create(@RequestBody UserProfileCreationRequest request) {
        return userProfileService.createProfile(request);
    }
}
