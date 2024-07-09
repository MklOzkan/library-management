package com.project.librarymanagement.service.user;

import com.project.librarymanagement.entity.user.User;
import com.project.librarymanagement.exception.BadRequestException;
import com.project.librarymanagement.payload.request.authentication.LoginRequest;
import com.project.librarymanagement.payload.request.authentication.UpdatePasswordRequest;
import com.project.librarymanagement.payload.response.authentication.AuthResponse;
import com.project.librarymanagement.payload.response.user.UserResponse;
import com.project.librarymanagement.repository.user.UserRepository;
import com.project.librarymanagement.security.jwt.JwtUtils;
import com.project.librarymanagement.security.service.UserDetailsImpl;
import com.project.librarymanagement.service.helper.MethodHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MethodHelper methodHelper;
    private final ModelMapper modelMapper;


    public AuthResponse authenticateUser(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        //injection of spring security authentication in service layer
        Authentication authentication =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(email,password));

        //validated authentication info is upload to security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.generateToken(authentication);

        //get all info for user
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        List<String> userRoles = new ArrayList<>(roles);

        //different way of using builder design pattern
        AuthResponse.AuthResponseBuilder responseBuilder = AuthResponse.builder();
        responseBuilder.token(token);
        responseBuilder.email(email);
        responseBuilder.role(userRoles);
        responseBuilder.name(userDetails.getName());
        return responseBuilder.build();
    }

    public void updatePassword(UpdatePasswordRequest updatePasswordRequest,
                               HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        User user = userRepository.findByEmail(email);
        methodHelper.checkBuildIn(user);
        //validate old password is correct
        if(passwordEncoder.matches(updatePasswordRequest.getNewPassword(),user.getPassword())){
            throw new BadRequestException("Passwords are not matched");
        }
        user.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

    public UserResponse findByEmail(HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("email");
        User user = methodHelper.loadUserByEmail(email);
        return modelMapper.map(user, UserResponse.class);
    }
    //notes
}
