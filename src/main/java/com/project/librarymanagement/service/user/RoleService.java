package com.project.librarymanagement.service.user;


import com.project.librarymanagement.entity.enums.RoleType;
import com.project.librarymanagement.entity.user.Role;
import com.project.librarymanagement.exception.ResourceNotFoundException;
import com.project.librarymanagement.repository.user.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getRole(RoleType roleType){
        List<Role> roles = roleRepository.findByEnumRoleEquals(roleType);
        if (roles.isEmpty()){
            throw new ResourceNotFoundException("Role not found");
        }
        return roles;
    }

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

}
