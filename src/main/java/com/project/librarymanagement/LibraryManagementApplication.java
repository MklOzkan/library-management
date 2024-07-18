package com.project.librarymanagement;

import com.project.librarymanagement.entity.enums.Gender;
import com.project.librarymanagement.entity.enums.RoleType;
import com.project.librarymanagement.entity.user.Role;
import com.project.librarymanagement.payload.request.user.UserRequest;
import com.project.librarymanagement.repository.user.RoleRepository;
import com.project.librarymanagement.service.user.RoleService;
import com.project.librarymanagement.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.*;

import java.time.LocalDate;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
public class LibraryManagementApplication implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;
    private final RoleRepository roleRepository;

    public LibraryManagementApplication(RoleService roleService, UserService userService, RoleRepository roleRepository) {
        this.roleService = roleService;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleService.getAllRoles().isEmpty()) {
            Role admin = new Role();
            admin.setRoleType(RoleType.ADMIN);
            admin.setRoleName("Admin");
            roleRepository.save(admin);

            Role employee = new Role();
            employee.setRoleType(RoleType.EMPLOYEE);
            employee.setRoleName("Employee");
            roleRepository.save(employee);

            Role member = new Role();
            member.setRoleType(RoleType.MEMBER);
            member.setRoleName("Member");
            roleRepository.save(member);
        }

        if(userService.getAllUsers().isEmpty()){
            UserRequest userRequest = getUserRequest();
            userService.saveUser(userRequest, "Admin");
        }
    }

    private UserRequest getUserRequest(){
        UserRequest userRequest = new UserRequest();
        userRequest.setPassword("admin01+");
        userRequest.setFirstName("AdminName");
        userRequest.setLastName("AdminLastName");
        userRequest.setBirthDate(LocalDate.of(1990, 1, 1));
        userRequest.setAddress("AdminAddress");
        userRequest.setPhoneNumber("111-111-1111");
        userRequest.setEmail("ozkan@gmail.com");
        userRequest.setGender(Gender.MALE);
        userRequest.setBuildIn(true);
        return userRequest;
    }

}
