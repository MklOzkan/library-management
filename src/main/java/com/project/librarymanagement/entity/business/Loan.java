package com.project.librarymanagement.entity.business;

import jakarta.persistence.*;

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
        private Boolean active;
        private String notes;

        @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
        private List<Book> books;

        @PrePersist
        public void prePersist() {
                this.loanDate = LocalDateTime.now();
        }

        @PostPersist
        public void postPersist() {
                if (user.getScore()>=2){
                        this.expireDate = this.loanDate.plusDays(20);
                } else if (user.getScore()==1) {
                        this.expireDate = this.loanDate.plusDays(15);
                } else if (user.getScore()==0) {
                        this.expireDate = this.loanDate.plusDays(10);
                }else if (user.getScore()==-1) {
                        this.expireDate = this.loanDate.plusDays(6);
                }else {
                        this.expireDate = this.loanDate.plusDays(3);
                }

        }



}
