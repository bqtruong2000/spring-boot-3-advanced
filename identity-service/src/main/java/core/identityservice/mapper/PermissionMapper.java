package core.identityservice.mapper;

import core.identityservice.dto.request.PermissionRequest;
import core.identityservice.dto.request.UserCreationRequest;
import core.identityservice.dto.request.UserUpdateRequest;
import core.identityservice.dto.response.PermissionResponse;
import core.identityservice.dto.response.UserResponse;
import core.identityservice.entity.Permission;
import core.identityservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

}
