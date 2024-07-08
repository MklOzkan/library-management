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

    public Role getRole(RoleType roleType){
        return roleRepository.findByEnumRoleEquals(roleType)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }
}
