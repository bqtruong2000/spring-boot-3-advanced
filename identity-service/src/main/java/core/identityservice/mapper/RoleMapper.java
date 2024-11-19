package core.identityservice.mapper;

import core.identityservice.dto.request.PermissionRequest;
import core.identityservice.dto.request.RoleRequest;
import core.identityservice.dto.response.PermissionResponse;
import core.identityservice.dto.response.RoleResponse;
import core.identityservice.entity.Permission;
import core.identityservice.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

}
