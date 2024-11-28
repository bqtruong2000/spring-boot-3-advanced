package core.postservice.controller;

import core.postservice.dto.ApiResponse;
import core.postservice.dto.PageResponse;
import core.postservice.dto.request.PostRequest;
import core.postservice.dto.response.PostResponse;
import core.postservice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    PostService postService;

    @PostMapping
    public ApiResponse<PostResponse> create(@RequestBody PostRequest request){
        return ApiResponse.<PostResponse>builder()
                .result(postService.create(request))
                .build();
    }

    @GetMapping("/my-posts")
    public ApiResponse<PageResponse<PostResponse>> getAllPosts(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size){
        return ApiResponse.<PageResponse<PostResponse>>builder()
                .result(postService.getMyPosts(page, size))
                .build();
    }
}
