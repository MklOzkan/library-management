package com.project.librarymanagement.validator;

import com.project.librarymanagement.entity.user.User;
import com.project.librarymanagement.exception.ConflictException;
import com.project.librarymanagement.payload.request.abstracts.AbstractUserRequest;
import com.project.librarymanagement.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {
  
  private final UserRepository userRepository;
  
  
  public void checkUniqueProperties(User user, AbstractUserRequest userRequest){
    String updatedPhone = "";
    String updatedEmail = "";
    boolean isChanged = false;
    //we are checking that if we change the unique properties

    if (!user.getEmail().equalsIgnoreCase(userRequest.getEmail())) {
      updatedEmail = userRequest.getEmail();
      isChanged = true;
    }
    if (!user.getPhoneNumber().equalsIgnoreCase(userRequest.getPhoneNumber())) {
      updatedPhone = userRequest.getPhoneNumber();
      isChanged = true;
    }
    if(isChanged){
      checkDuplicate(updatedPhone,updatedEmail);
    }
  }
  
  public void checkDuplicate( String phone, String email) {
    if(userRepository.existsByPhoneNumber(phone)){
      throw new ConflictException("Already exists" + phone);
    }
    if(userRepository.existsByEmail(email)){
      throw new ConflictException("Already exists" +email);
    }
  }

}
