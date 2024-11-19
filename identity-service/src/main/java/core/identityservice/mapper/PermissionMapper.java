package core.identityservice.mapper;

import core.identityservice.dto.request.PermissionRequest;
import core.identityservice.dto.response.PermissionResponse;
import core.identityservice.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

}
