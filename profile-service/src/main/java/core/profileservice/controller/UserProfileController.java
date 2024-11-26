package core.profileservice.controller;

import core.profileservice.dto.response.UserProfileResponse;
import core.profileservice.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/all")
    public List<UserProfileResponse> getAll() {
        return userProfileService.getAllProfiles();
    }

}
