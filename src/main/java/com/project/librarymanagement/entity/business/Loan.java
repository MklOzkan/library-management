package com.project.librarymanagement.entity.business;

import javax.persistence.*;

import com.project.librarymanagement.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @ManyToOne
        @JoinColumn(name = "userId")
        private User user;
        private LocalDateTime loanDate;
        private LocalDateTime expireDate;
        private LocalDateTime returnDate;
        private String notes;

        @OneToMany
        private List<Book> books;



}
