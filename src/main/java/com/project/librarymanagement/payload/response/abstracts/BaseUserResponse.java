package com.project.librarymanagement.payload.response.abstracts;

import com.project.librarymanagement.entity.business.Loan;
import com.project.librarymanagement.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseUserResponse {
  
  private Long userId;
  private String firstName;
  private String lastLame;
  private LocalDate birthDay;
  private int score;
  private String address;
  private String phoneNumber;
  private Gender gender;
  private String email;
  private String role;
  private Loan loan;

}
