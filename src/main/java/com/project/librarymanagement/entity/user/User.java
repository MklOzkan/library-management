package com.project.librarymanagement.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.librarymanagement.entity.business.Loan;
import com.project.librarymanagement.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String username;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int score;
    private String address;
    @Column(unique = true)
    private String phoneNumber;
    private LocalDate birthDate;
    @Column(unique = true)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+3")
    private LocalDateTime createDate = LocalDateTime.now();

    private Boolean active;

    private Boolean builtIn;

    @ManyToMany()
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Loan> loans;

}
