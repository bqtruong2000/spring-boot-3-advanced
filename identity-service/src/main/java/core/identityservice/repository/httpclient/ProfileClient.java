package core.identityservice.repository.httpclient;

import core.identityservice.dto.request.ProfileCreationRequest;
import core.identityservice.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service", url = "${app.service.profile}")
public interface ProfileClient {
    @PostMapping(value = "/internal/users/create", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse createProfile(@RequestBody ProfileCreationRequest request);
}
